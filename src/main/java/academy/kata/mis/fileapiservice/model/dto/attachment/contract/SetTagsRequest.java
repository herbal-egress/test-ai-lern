package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.dto.attachment.TagDto;

import java.util.List;

public record SetTagsRequest(Long employeeId,
                             List<Long> attachmentsIds,
                             List<TagDto> tags) {
}
