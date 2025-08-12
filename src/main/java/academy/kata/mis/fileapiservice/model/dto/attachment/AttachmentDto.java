package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AttachmentDto(Long id,
                            String name,
                            EmployeeDto creator,
                            LocalDateTime createDatetime,
                            EmployeeDto locker,
                            LocalDateTime lockedDatetime,
                            List<AttachmentVersionDto> versions) {
}
