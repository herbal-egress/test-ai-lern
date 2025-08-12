package academy.kata.mis.fileapiservice.model.exceptions;

public class MinioExceptions {

    // Кастомное исключение для ошибок подключения к MinIO
    public static class MinioConnectionException extends RuntimeException {
        public MinioConnectionException(String message, Throwable cause) {
            super(message, cause); // Передаем сообщение и причину в родительский класс
        }
    }

    // Кастомное исключение для операций с бакетами MinIO
    public static class MinioBucketOperationException extends RuntimeException {
        public MinioBucketOperationException(String message, Throwable cause) {
            super(message, cause); // Передаем сообщение и причину в родительский класс
        }
    }

    // Кастомное исключение для ошибок загрузки файлов в MinIO
    public static class MinioUploadException extends RuntimeException {
        public MinioUploadException(String message, Throwable cause) {
            super(message, cause); // Передаем сообщение и причину в родительский класс
        }

        public MinioUploadException(String message) {
            super(message); // Альтернативный конструктор только с сообщением
        }

    }
}