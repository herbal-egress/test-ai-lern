package academy.kata.mis.fileapiservice.model.entity.attachment;

import academy.kata.mis.fileapiservice.model.entity.Employee;
import academy.kata.mis.fileapiservice.model.enums.attachment.AttachmentStatus;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * версия загруженного вложения
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachment_versions")
public class AttachmentVersion {

    /**
     * первичный ключ. генерируется тут
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * номер версии
     */
    @Column(name = "number", nullable = false)
    private Integer number;

    /**
     * статус вложения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttachmentStatus status;

    /**
     * тот кто загрузил эту версию
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater_id", nullable = false)
    private Employee updater;

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
     * тэги. ключ=значение
     */
    @Column(name = "tags")
    private ArrayNode tags;

    /**
     * id файла
     */
    @Column(name = "file_id", nullable = false)
    private UUID fileId;

    /**
     * вложение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentVersion that = (AttachmentVersion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
