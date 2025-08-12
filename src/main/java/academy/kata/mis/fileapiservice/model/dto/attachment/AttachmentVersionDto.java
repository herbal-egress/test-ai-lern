package academy.kata.mis.fileapiservice.model.dto.attachment;

import academy.kata.mis.fileapiservice.model.enums.attachment.AttachmentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record AttachmentVersionDto(Long id,
                                   Integer number,
                                   Boolean isCurrentVersion,
                                   AttachmentStatus status,
                                   EmployeeDto updater,
                                   LocalDateTime updateTime,
                                   BigDecimal size, //в MByte например 1,24 MByte
                                   List<TagDto> tags,
                                   UUID fileId) {
}
