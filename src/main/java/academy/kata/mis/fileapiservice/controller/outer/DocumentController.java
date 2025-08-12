package academy.kata.mis.fileapiservice.controller.outer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/file-api/documents")
public class DocumentController {

    /*
    Эндпоинт скачивания временного документа пользователем

    необходимо проверить что:
    - переданный документ существует
    - авторизованный пользователь является владельцем документа

    необходимо:
    - скачать документ
     */
    public byte[] downloadDocument(UUID documentId) {
        return null;
    }
}
