package academy.kata.mis.fileapiservice.controller.internal;

import academy.kata.mis.fileapiservice.model.enums.Category;
import academy.kata.mis.fileapiservice.model.enums.DocumentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/internal/file-api/documents")
public class InternalDocumentController {

    /*
    Эндпоинт загрузки документа для временного хранения

    необходимо:
    - схранить документ в соответсвии с его категорией
    - добавить параметры по необходимости
    - сохранить в бакет минио document/{uuid}
    - в соответсвии с типом документа установить время жизни документа
     */
    public void uploadDocument(Long organizationId,
                               UUID ownerUserId,
                               Category category,
                               String filename,
                               DocumentType documentType,
                               byte[] fileBody) {
    }
}