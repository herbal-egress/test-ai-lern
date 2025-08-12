package academy.kata.mis.fileapiservice.model.entity.attachment;

import academy.kata.mis.fileapiservice.model.entity.MedicalOrganization;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

/**
 * директория в которой находятся вложения
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "folders")
public class Folder {

    /**
     * первичный ключ. генерируется тут
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * путь - может быть как folder так и folder/inner/inner
     */
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * вложения в директории
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "folder")
    private Set<Attachment> attachments;

    /**
     * оргинизация которой принадлежат директории и их вложения
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private MedicalOrganization organization;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
