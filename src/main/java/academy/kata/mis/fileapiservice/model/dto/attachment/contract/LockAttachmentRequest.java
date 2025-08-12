package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.enums.attachment.LockMethod;

import java.util.List;

public record LockAttachmentRequest(Long employeeId,
                                    LockMethod method,
                                    List<Long> attachmentsIds) {
}
