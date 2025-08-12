package academy.kata.mis.fileapiservice.model.exceptions;

public class NotificationExceptions {

    // Исключение для ошибок подготовки уведомления
    public static class NotificationPreparationException extends RuntimeException {
        public NotificationPreparationException(String message) {
            super(message); // Конструктор только с сообщением
        }
    }

    // Исключение для ошибок отправки уведомления
    public static class NotificationSendException extends RuntimeException {
        public NotificationSendException(String message, Throwable cause) {
            super(message, cause); // Конструктор с сообщением и причиной
        }
    }

    // Исключение для исчерпания попыток отправки
    public static class NotificationRetryExhaustedException extends RuntimeException {
        public NotificationRetryExhaustedException(String message, Throwable cause) {
            super(message, cause); // Конструктор с сообщением и причиной
        }
    }
}
