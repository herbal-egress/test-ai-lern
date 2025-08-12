package academy.kata.mis.fileapiservice.service.impl;

import academy.kata.mis.fileapiservice.config.MinioProperties;
import academy.kata.mis.fileapiservice.model.exceptions.MinioExceptions;
import academy.kata.mis.fileapiservice.service.MinioService;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 1000;

    @Override
    public void uploadWithRetry(byte[] fileBody, String objectPath) {
        doUploadWithRetry(fileBody, objectPath, MAX_RETRY_ATTEMPTS);
    }

    private void doUploadWithRetry(byte[] fileBody, String objectPath, int attempts) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(objectPath)
                            .stream(
                                    new ByteArrayInputStream(fileBody),
                                    fileBody.length,
                                    -1
                            ).build()
            );
            log.debug("Файл успешно загружен в MinIO: {}", objectPath);
        } catch (Exception e) {
            log.error("Ошибка загрузки в MinIO (осталось попыток: {}): {}", attempts, e.getMessage());

            if (attempts > 1) {
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                    doUploadWithRetry(fileBody, objectPath, attempts - 1);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Прервано ожидание перед повторной загрузкой: {}", ie.getMessage());
                    throw new MinioExceptions.MinioUploadException("Прервана загрузка в MinIO", ie);
                }
            } else {
                throw new MinioExceptions.MinioUploadException(String.format(
                        "Не удалось загрузить файл в MinIO после %d попыток. Путь: %s",
                        MAX_RETRY_ATTEMPTS, objectPath), e);
            }
        }
    }
}
