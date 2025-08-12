package academy.kata.mis.fileapiservice.config;

import academy.kata.mis.fileapiservice.model.exceptions.MinioExceptions;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {
    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        log.debug("Инициализация MinIO клиента для endpoint: {}", properties.getEndpoint());
        try {
            MinioClient client = MinioClient.builder()
                    .endpoint(properties.getEndpoint())
                    .credentials(properties.getAccessKey(), properties.getSecretKey())
                    .build();
            validateConnection(client);
            initBucket(client, properties.getBucket());
            return client;
        } catch (Exception e) {
            String errorMsg = String.format("Ошибка подключения к MinIO (%s): %s",
                    properties.getEndpoint(), e.getMessage());
            log.error(errorMsg);
            throw new MinioExceptions.MinioConnectionException(errorMsg, e);
        }
    }

    private void validateConnection(MinioClient client) {
        try {
            client.listBuckets();
        } catch (Exception e) {
            throw new MinioExceptions.MinioConnectionException("Проверка подключения к MinIO не удалась", e);
        }
    }

    private void initBucket(MinioClient client, String bucket) {
        try {
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                log.info("Бакет '{}' создан", bucket);
            } else {
                log.debug("Бакет '{}' уже существует", bucket);
            }
        } catch (Exception e) {
            String errorMsg = String.format("Ошибка работы с бакетом '%s': %s", bucket, e.getMessage());
            log.error(errorMsg);
            throw new MinioExceptions.MinioBucketOperationException(errorMsg, e);
        }
    }
}