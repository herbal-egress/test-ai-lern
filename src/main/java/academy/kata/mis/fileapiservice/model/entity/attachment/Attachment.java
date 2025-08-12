package academy.kata.mis.fileapiservice.model.entity.attachment;

import academy.kata.mis.fileapiservice.model.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * Вложение - некий файл любого типа
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment {

    /**
     * первичный ключ. генерируется тут
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * имя вложения document.word
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * пользователь заблокировавший вложение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id")
    private Employee locker;

    /**
     * дата блокировки
     */
    @Column(name = "locked_datetime")
    private LocalDateTime lockedDatetime;

    /**
     * создатель вложения
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee creator;

    /**
     * дата создания
     */
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    /**
     * текущая версия вложения
     */
    @OneToOne
    @JoinColumn(name = "current_version_id")
    private AttachmentVersion currentVersion;

    /**
     * все версии вложения
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attachment")
    private Set<AttachmentVersion> versions;

    /**
     * директория
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
