package academy.kata.mis.fileapiservice.service;

public interface MinioService {
    void uploadWithRetry(byte[] fileBody, String objectPath);
}