package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import java.util.List;

public record CopyAttachmentRequest(Long employeeId,
                                    List<Long> attachmentSourceIds,
                                    String targetPath) {
}
