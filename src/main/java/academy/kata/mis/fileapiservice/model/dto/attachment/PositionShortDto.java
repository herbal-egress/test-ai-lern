package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

@Builder
public record PositionShortDto(long id,
                               String name) {
}
