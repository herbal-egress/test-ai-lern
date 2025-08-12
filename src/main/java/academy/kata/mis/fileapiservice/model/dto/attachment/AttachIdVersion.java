package academy.kata.mis.fileapiservice.model.dto.attachment;

import jakarta.validation.constraints.NotNull;

public record AttachIdVersion(@NotNull Long attachmentId,
                              Integer version) {
}
