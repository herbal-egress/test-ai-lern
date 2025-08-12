package academy.kata.mis.fileapiservice.model.dto.attachment.contract;

import academy.kata.mis.fileapiservice.model.dto.attachment.AttachIdVersion;

import java.util.List;

public record TruncateAttachmentsRequest(Long organizationId,
                                         Long folderId,
                                         List<AttachIdVersion> attachments) {
}
