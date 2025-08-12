package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

@Builder
public record PersonShortDto(long personId,
                             String firstName,
                             String lastName,
                             String patronymic) {
}
