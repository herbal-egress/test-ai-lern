package academy.kata.mis.fileapiservice.kafka.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationMessage(
        @NotNull UUID userId,            // Кому отправлено
        String title,           // Заголовок ("Документ загружен")
        String content,         // Текст ("Файл успешно обработан")
        LocalDateTime createdAt
) {}