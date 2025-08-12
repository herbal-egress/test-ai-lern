package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.dto.attachment.FolderWithAttachmentsDto;

import java.util.List;

public record AttachmentsWithPaginationResponse(Integer totalSize,
                                                List<FolderWithAttachmentsDto> folders) {
}
