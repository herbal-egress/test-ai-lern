package academy.kata.mis.fileapiservice.model.entity;

import academy.kata.mis.fileapiservice.model.entity.attachment.Folder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Медицинская организация
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_organizations")
public class MedicalOrganization {

    /**
     * id генерируется в mis_structure_service
     */
    @Id
    @Column(name = "medical_organization_id")
    private Long medicalOrganizationId;

    /**
     * Сотрудники медицинской организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<Employee> employees;

    /**
     * персоны медицинской организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<Person> persons;

    /**
     * документы медицинской организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<Document> documents;

    /**
     * директории медицинской организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<Folder> folders;

    public void addFolder(Folder folder) {
        if (folders == null) {
            folders = new ArrayList<>();
        }
        folders.add(folder);
        folder.setOrganization(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalOrganization that = (MedicalOrganization) o;
        return Objects.equals(medicalOrganizationId, that.medicalOrganizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(medicalOrganizationId);
    }
}
