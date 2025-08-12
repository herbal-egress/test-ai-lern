package academy.kata.mis.fileapiservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaErrorConfig {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public ConsumerAwareListenerErrorHandler kafkaErrorHandler() {
        return (message, exception, consumer) -> {
            String originalPayload = message.getPayload().toString();
            String dltMessage = String.format(
                    "Ошибка обработки сообщения: %s. Изначальное сообщение: %s",
                    exception.getMessage(),
                    originalPayload
            );
            log.error("Ошибка в file-api.save-document: {}", dltMessage);
            kafkaTemplate.send("file-api.save-document.dlt", dltMessage);
            return null;
        };
    }

    @Bean
    public ConsumerAwareListenerErrorHandler kafkaTestErrorHandler() {
        return (message, exception, consumer) -> {
            String originalPayload = message.getPayload().toString();
            String dltMessage = String.format(
                    "Ошибка обработки ТЕСТОВОГО сообщения из 'file-api.save-document-test': %s. Изначальное сообщение: %s",
                    exception.getMessage(),
                    originalPayload
            );
            log.error("Ошибка в file-api.save-document-test: {}", dltMessage);
            kafkaTemplate.send("file-api.save-document-test.dlt", dltMessage);
            return null;
        };
    }
}