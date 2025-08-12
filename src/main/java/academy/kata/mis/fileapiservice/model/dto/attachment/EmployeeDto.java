package academy.kata.mis.fileapiservice.model.dto.attachment;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(long employeeId,
                          UUID userId,
                          PersonShortDto person,
                          PositionShortDto position) {

}
