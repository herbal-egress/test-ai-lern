package academy.kata.mis.fileapiservice.kafka.dto;

import academy.kata.mis.fileapiservice.model.enums.Category;
import academy.kata.mis.fileapiservice.model.enums.DocumentType;

import java.util.UUID;

public record SaveDocument(Long organizationId,
                           UUID ownerUserId,
                           Category category,
                           String filename,
                           DocumentType documentType,
                           byte[] fileBody) {
}
