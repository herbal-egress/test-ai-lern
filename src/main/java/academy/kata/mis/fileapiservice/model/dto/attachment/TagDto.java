package academy.kata.mis.fileapiservice.model.dto.attachment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TagDto(@NotBlank String key,
                     String value) {
}
