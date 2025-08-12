package academy.kata.mis.fileapiservice.model.entity.attachment;

import academy.kata.mis.fileapiservice.model.enums.attachment.AttachmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "archive_attachments")
public class ArchiveAttachment {

    /**
     * первичный ключ. генерируется тут
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * userId пользователя который удалил вложение
     */
    @Column(name = "who_delete")
    private UUID whoDelete;

    /**
     * дата и время удаления
     */
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDatetime;

    /**
     * id организации
     */
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    /**
     * id директории
     */
    @Column(name = "folder_id", nullable = false)
    private Long folderId;

    /**
     * путь - может быть как folder так и folder/inner/inner
     */
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * id вложения
     */
    @Column(name = "attachment_id", nullable = false)
    private Long attachmentId;

    /**
     * имя вложения
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * статус вложения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttachmentStatus status;

    /**
     * UUID пользователя создавшего вложение
     */
    @Column(name = "creator_id", nullable = false)
    private UUID creatorId;

    /**
     * дата создания
     */
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    /**
     * id версии вложения
     */
    @Column(name = "version_id", nullable = false)
    private Long versionId;

    /**
     * номер версии
     */
    @Column(name = "number", nullable = false)
    private Integer number;

    /**
     * UUID пользователя загрузившего эту версию вложения
     */
    @Column(name = "updater_id", nullable = false)
    private UUID updaterId;

    /**
     * дата загрузки этой версии
     */
    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDatetime;

    /**
     * размер этой версии
     */
    @Column(name = "size", nullable = false)
    private Long size;

    /**
     * id файла
     */
    @Column(name = "file_id", nullable = false)
    private UUID fileId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveAttachment that = (ArchiveAttachment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
