package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.dto.attachment.FolderWithAttachmentsDto;
import lombok.Builder;

import java.util.List;

@Builder
public record AttachmentsResponse(List<FolderWithAttachmentsDto> folders) {
}
