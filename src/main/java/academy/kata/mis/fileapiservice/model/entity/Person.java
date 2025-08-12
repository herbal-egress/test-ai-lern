package academy.kata.mis.fileapiservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * пациент
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class Person {

    /**
     * id генерируется в mis_person_service
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private long personId;

    /**
     * id автоизованного пользователя
     * id генерируется в mis_auth_service
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * медицинская организация
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(personId);
    }
}
