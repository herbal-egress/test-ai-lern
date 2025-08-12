package academy.kata.mis.fileapiservice;

import academy.kata.mis.auditclient.config.KafkaAuditAutoConfiguration;
import academy.kata.mis.fileapiservice.config.DocumentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(DocumentProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Import({KafkaAuditAutoConfiguration.class})
public class FileApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApiServiceApplication.class, args);
    }
}