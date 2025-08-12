package academy.kata.mis.fileapiservice.controller;

import academy.kata.mis.fileapiservice.model.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(MissingCategoryHeaderException.class)
    public ResponseEntity<String> handleMissingCategoryHeader(MissingCategoryHeaderException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<String> handleInvalidCategory(InvalidCategoryException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<String> handleOrganizationNotFound(OrganizationNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<String> handleFileSizeExceeded(FileSizeExceededException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body("Внутренняя ошибка сервера");
    }

    @ExceptionHandler(MinioExceptions.MinioConnectionException.class)
    public ResponseEntity<String> handleMinioConnection(MinioExceptions.MinioConnectionException ex) {
        return ResponseEntity.status(503).body(ex.getMessage());
    }

    @ExceptionHandler(MinioExceptions.MinioBucketOperationException.class)
    public ResponseEntity<String> handleMinioBucket(MinioExceptions.MinioBucketOperationException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    @ExceptionHandler(MinioExceptions.MinioUploadException.class)
    public ResponseEntity<String> handleMinioUpload(MinioExceptions.MinioUploadException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    @ExceptionHandler(NotificationExceptions.NotificationPreparationException.class)
    public ResponseEntity<String> handleNotificationPreparation(NotificationExceptions.NotificationPreparationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NotificationExceptions.NotificationSendException.class)
    public ResponseEntity<String> handleNotificationSend(NotificationExceptions.NotificationSendException ex) {
        return ResponseEntity.status(502).body(ex.getMessage());
    }

    @ExceptionHandler(NotificationExceptions.NotificationRetryExhaustedException.class)
    public ResponseEntity<String> handleNotificationRetryExhausted(NotificationExceptions.NotificationRetryExhaustedException ex) {
        return ResponseEntity.status(504).body(ex.getMessage());
    }
}