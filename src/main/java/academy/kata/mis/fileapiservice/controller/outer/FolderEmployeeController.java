package academy.kata.mis.fileapiservice.controller.outer;

import academy.kata.mis.fileapiservice.model.dto.attachment.contract.FolderResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-api/folders")
public class FolderEmployeeController {

    /*
    Авторизованный пользователь получает директории по переданному пути

    необходимо проверить:
    - авторизованный пользователь является переданным сотрудником
    - переданный сотрудник существует
    - переданный сотрудник имеет статус работающего (в сервисе структуры)

    параметры:
    - path - опциональный параметр - путь, если не передан то возвращаются все директории в бакете

    необходимо сделать:
    - вернуть все папки по переданному пути для организации сотруднкика
     */
    public FolderResponse getFolders(Long employeeId,
                                     String path) {
        return null;
    }

    /*
    Авторизованный пользователь создает директорию по переданному пути

    необходимо проверить:
    - авторизованный пользователь является переданным сотрудником
    - переданный сотрудник существует
    - переданный сотрудник имеет статус работающего (в сервисе структуры)
    - переданный путь не null
    - в организации переданного сотрудника нет директорий с переданным путем

    необходимо сделать:
    - создать директорию
    - отправить запись в аудит
     */
    public FolderResponse addFolder(Long employeeId,
                                    String path) {
        return null;
    }
}
