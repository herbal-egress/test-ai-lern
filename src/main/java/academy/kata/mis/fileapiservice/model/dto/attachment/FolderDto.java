package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

@Builder
public record FolderDto(Long id,
                        String path) {
}
