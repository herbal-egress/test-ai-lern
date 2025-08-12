package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

import java.util.List;

@Builder
public record FolderWithAttachmentsDto(Long id,
                                       String path,
                                       List<AttachmentDto> attachments) {
}
