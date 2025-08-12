package academy.kata.mis.fileapiservice.kafka.dto;

import academy.kata.mis.fileapiservice.model.enums.Category;
import academy.kata.mis.fileapiservice.model.enums.DocumentType;

import java.time.LocalDateTime;
import java.util.UUID;
public record SaveDocumentToKafkaDto(
        Long organizationId,
        UUID ownerUserId,
        Category category,
        String filename,
        DocumentType documentType,
        byte[] fileBody,
        long size,
        LocalDateTime expirationDatetime,
        UUID documentUuid  // поле для генерации UUID конкретного документа
) {
    // Конструктор с генерацией UUID, если UUID не передан при создании этого ДТО
    public SaveDocumentToKafkaDto {
        documentUuid = documentUuid != null ? documentUuid : UUID.randomUUID();
    }
}