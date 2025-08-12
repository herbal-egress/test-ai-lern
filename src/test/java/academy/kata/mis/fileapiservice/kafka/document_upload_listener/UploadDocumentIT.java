package academy.kata.mis.fileapiservice.kafka.document_upload_listener;

import academy.kata.mis.fileapiservice.dto.feign.OrganizationDto;
import academy.kata.mis.fileapiservice.feign.StructureFeignClient;
import academy.kata.mis.fileapiservice.kafka.DocumentUploadListener;
import academy.kata.mis.fileapiservice.kafka.dto.SaveDocument;
import academy.kata.mis.fileapiservice.model.enums.Category;
import academy.kata.mis.fileapiservice.model.enums.DocumentType;
import academy.kata.mis.fileapiservice.model.exceptions.MissingCategoryHeaderException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("local")
class UploadDocumentIT {

    @Autowired
    private KafkaTemplate<String, SaveDocument> kafkaTemplate;

    @Autowired
    private StructureFeignClient structureFeignClient;

    @Autowired
    private DocumentUploadListener documentUploadListener;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    private final long organizationid = 1L;

    @KafkaListener(topics = "file-api.save-document-test", errorHandler = "kafkaTestErrorHandler")
    private void uploadDocumentTest(ConsumerRecord<String, SaveDocument> record) {
        documentUploadListener.uploadDocument(record);  // Использую бизнес-логику тестируемого метода
    }

    @Test
    void ValidDocument_checkAllOkay() throws InterruptedException {
// Подготовка валидных данных
        OrganizationDto organization = structureFeignClient.getOrganizationById(organizationid);
        assertNotNull(organization, "Организация должна существовать в тестовом окружении");
        RecordHeaders header = (RecordHeaders) new RecordHeaders().add("category", "REPORT".getBytes());
        byte[] testFileBody = ("Тестовое содержимое файла.\n" + organization).getBytes();
        SaveDocument testDocument = new SaveDocument(
                organizationid,
                UUID.randomUUID(),
                Category.REPORT,
                "checkAllOkay.doc",
                DocumentType.DOC,
                testFileBody
        );
        ProducerRecord<String, SaveDocument> producerRecord =
                new ProducerRecord<>("file-api.save-document-test", null, null, null, testDocument, header);
        kafkaTemplate.send(producerRecord);
        Thread.sleep(3000);
    }

    @Test
    void CategoryHeaderMissing_checkThrowAndDLT() throws InterruptedException {
// 1. документ без заголовка category
        SaveDocument testDocument = new SaveDocument(
                organizationid,
                UUID.randomUUID(),
                null,
                "checkThrowAndDLT.doc",
                DocumentType.DOC,
                "Test_CategoryHeaderMissing_checkThrowAndDLT".getBytes()
        );
        kafkaTemplate.send("file-api.save-document-test", testDocument);
        Thread.sleep(3000);
// 2. ConsumerRecord БЕЗ заголовка "category"
        ConsumerRecord<String, SaveDocument> record = new ConsumerRecord<>(
                "file-api.save-document-test", 0, 0L, null, testDocument);
// 3. Проверка, что в uploadDocument выбрасывается исключение (метод не отрабатывает)
        assertThrows(MissingCategoryHeaderException.class, () ->
                uploadDocumentTest(record));
    }
}