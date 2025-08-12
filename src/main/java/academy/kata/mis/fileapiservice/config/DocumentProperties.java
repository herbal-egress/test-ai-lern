package academy.kata.mis.fileapiservice.config;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "document")
public class DocumentProperties {
    @Positive(message = "Срок действия документа должен быть больше нуля")
    private int expirationHours;
    @Positive
    private int maxFileSizeMb;

    public long getMaxFileSizeBytes() {
        return maxFileSizeMb * 1024L * 1024L;
    }
}