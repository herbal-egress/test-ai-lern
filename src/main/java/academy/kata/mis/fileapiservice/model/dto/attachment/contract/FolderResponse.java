package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.dto.attachment.FolderDto;

import java.util.List;

public record FolderResponse(Long id,
                             String path,
                             List<FolderDto> folders) {
}
