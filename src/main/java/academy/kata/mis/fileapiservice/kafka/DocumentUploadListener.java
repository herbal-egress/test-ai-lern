package academy.kata.mis.fileapiservice.kafka;

import academy.kata.mis.fileapiservice.dto.feign.OrganizationDto;
import academy.kata.mis.fileapiservice.feign.StructureFeignClient;
import academy.kata.mis.fileapiservice.model.exceptions.*;
import academy.kata.mis.fileapiservice.service.MinioService;
import academy.kata.mis.fileapiservice.service.NotificationService;
import org.apache.kafka.common.header.Header;

import java.util.Arrays;

import academy.kata.mis.fileapiservice.config.DocumentProperties;
import academy.kata.mis.fileapiservice.kafka.dto.NotificationMessage;
import academy.kata.mis.fileapiservice.kafka.dto.SaveDocument;
import academy.kata.mis.fileapiservice.kafka.dto.SaveDocumentToKafkaDto;
import academy.kata.mis.fileapiservice.model.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentUploadListener {
    private final DocumentProperties documentProperties;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    private final StructureFeignClient structureFeignClient;
    private final MinioService minioService;
    private final NotificationService notificationService;

    // todo Димитрий Алексашкин
    /*
    сервис Кафки должен читать из топика сохранения документов file-api.save-document и сохранять себе в базу
    нужно:
    - создать (если нету) в проперти переменную "время жизни документа" и установить ее в 3 суток
    - найти медицинскую организацию иначе ошибка
    - установить документу Category которую он должен получить из заголовка в кафке
    - посчитать и установить size
    - высчитать expirationDatetime на основе переменной из проперти
    - отправить пользователю сообщение в notification-service
    - хранить документы по сгенерированному uuid в minio в бакете documents/{organizationId}/{documentUUID}
    - не создавай скрипты миграций для attachment
    в environment:
    - заменить топик file-api.save-report на file-api.save-document
    - создать топики file-api.save-document.dlt, file-api.save-document-test, file-api.save-document-test.dlt

    если не получилось прочитать сообщение или в заголовке неизвестный хэдер:
     - отправить сообщение строкой в dlt
     */

    @KafkaListener(topics = "file-api.save-document", // Аннотация для подписки на топики
            errorHandler = "kafkaErrorHandler") // Кастомный обработчик для DLT топика
    public void uploadDocument(ConsumerRecord<String, SaveDocument> document) {

        SaveDocument originalDocument = document.value();
        int expirationHours = documentProperties.getExpirationHours();
        log.debug("Срок действия документа установлен (час): {}", expirationHours);

        OrganizationDto organization = structureFeignClient.getOrganizationById(originalDocument.organizationId());
        if (organization == null) {
            String error = String.format("Организация с ID %d не найдена", originalDocument.organizationId());
            log.error(error);
            throw new OrganizationNotFoundException(error);
        }
        log.debug("Организация найдена: ID={}, название={}", organization.id(), organization.name());

        Header categoryHeader = document.headers().lastHeader("category");
        if (categoryHeader == null) {
            String error = "Отсутствует обязательный заголовок 'category'";
            log.error(error);
            throw new MissingCategoryHeaderException(error);
        }
        String categoryValue = new String(categoryHeader.value());
        Category category;
        try {
            category = Category.valueOf(categoryValue); // REPORT
        } catch (IllegalArgumentException e) {
            String error = String.format("Недопустимая категория '%s'. Допустимые значения: %s",
                    categoryValue,
                    Arrays.toString(Category.values())
            );
            log.error(error);
            throw new InvalidCategoryException(error);
        }
        log.debug("Категория документа определена: {}", category);

        long fileSize = originalDocument.fileBody().length;
        long maxSize = documentProperties.getMaxFileSizeBytes();
        if (fileSize > maxSize) {
            String error = String.format("Размер файла превышает допустимый лимит (%.2f MB > %d MB)",
                    fileSize / (1024.0 * 1024.0),
                    documentProperties.getMaxFileSizeMb()
            );
            log.error(error);
            throw new FileSizeExceededException(error);
        }
        log.debug("Размер файла OK: {} MB (max {} MB)", fileSize / (1024.0 * 1024.0), documentProperties.getMaxFileSizeMb());

        SaveDocumentToKafkaDto extendedDocument = new SaveDocumentToKafkaDto(
                originalDocument.organizationId(),
                originalDocument.ownerUserId(),
                category,
                originalDocument.filename(),
                originalDocument.documentType(),
                originalDocument.fileBody(),
                fileSize,
                LocalDateTime.now().plusHours(expirationHours),
                null  // когда null, UUID генерируется через конструктор
        );
        log.info("Документ подготовлен для сохранения. Организация ID: {}, файл: {}",
                originalDocument.organizationId(), extendedDocument.filename());

        minioService.uploadWithRetry(
                originalDocument.fileBody(),
                String.format("%s/%s.%s", extendedDocument.organizationId(), extendedDocument.documentUuid(), originalDocument.documentType()));

        NotificationMessage notificationMessage = new NotificationMessage(
                extendedDocument.ownerUserId(),
                "Новый документ загружен",
                String.format("Документ '%s' успешно загружен", extendedDocument.filename()),
                LocalDateTime.now()
        );
        notificationService.sendWithRetry(notificationMessage);
    }
}