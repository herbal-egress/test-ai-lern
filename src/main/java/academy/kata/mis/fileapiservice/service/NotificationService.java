package academy.kata.mis.fileapiservice.service;

import academy.kata.mis.fileapiservice.kafka.dto.NotificationMessage;

public interface NotificationService {
    void sendWithRetry(NotificationMessage notification);
}