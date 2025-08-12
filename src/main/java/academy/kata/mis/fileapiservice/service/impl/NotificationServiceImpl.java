package academy.kata.mis.fileapiservice.service.impl;

import academy.kata.mis.fileapiservice.kafka.dto.NotificationMessage;
import academy.kata.mis.fileapiservice.model.exceptions.NotificationExceptions;
import academy.kata.mis.fileapiservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 1000;

    @Override
    public void sendWithRetry(NotificationMessage notification) {
        doSendWithRetry(notification, MAX_RETRY_ATTEMPTS);
    }

    private void doSendWithRetry(NotificationMessage notification, int attempts) {
        if (notification == null || notification.userId() == null || notification.content() == null) {
            String errorMsg = "Некорректные данные уведомления";
            log.error(errorMsg);
            throw new NotificationExceptions.NotificationPreparationException(errorMsg);
        }

        try {
            log.debug("Попытка отправки уведомления (осталось попыток: {} из " + MAX_RETRY_ATTEMPTS + "), userId: {}",
                    attempts, notification.userId());
            CompletableFuture<SendResult<String, NotificationMessage>> future =
                    kafkaTemplate.send("notification.save", notification);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    handleNotificationError(notification, attempts, ex);
                } else {
                    log.info("Уведомление успешно отправлено. userId: {}, topic: {}, offset: {}",
                            notification.userId(),
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (Exception e) {
            handleNotificationError(notification, attempts, e);
        }
    }

    private void handleNotificationError(NotificationMessage notification,
                                         int attempts, Throwable ex) {
        log.error("Ошибка отправки уведомления (осталось попыток: {}), userId: {}: {}",
                attempts, notification.userId(), ex.getMessage());

        if (attempts > 1) {
            try {
                Thread.sleep(RETRY_DELAY_MS);
                doSendWithRetry(notification, attempts - 1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("Прервано ожидание перед повторной отправкой, userId: {}: {}",
                        notification.userId(), ie.getMessage());
                throw new NotificationExceptions.NotificationSendException(
                        String.format("Прервана отправка уведомления пользователю %s",
                                notification.userId()), ie);
            }
        } else {
            throw new NotificationExceptions.NotificationRetryExhaustedException(
                    String.format("Не удалось отправить уведомление пользователю %s после %d попыток",
                            notification.userId(), MAX_RETRY_ATTEMPTS), ex);
        }
    }
}