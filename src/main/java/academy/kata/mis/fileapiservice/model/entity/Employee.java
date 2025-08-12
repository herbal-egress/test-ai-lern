package academy.kata.mis.fileapiservice.model.entity;

import academy.kata.mis.fileapiservice.model.entity.attachment.Attachment;
import academy.kata.mis.fileapiservice.model.entity.attachment.AttachmentVersion;
import academy.kata.mis.fileapiservice.model.enums.EmployeeStatus;
import academy.kata.mis.fileapiservice.model.enums.PositionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

/**
 * сотрудники
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    /**
     * id генерируется в mis_structure_service
     */
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * id автоизованного пользователя
     * id генерируется в mis_auth_service
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * id персоны
     * id генерируется в mis_person_service
     */
    @Column(name = "person_id", nullable = false)
    private Long personId;

    /**
     * тип должности
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "position_type", nullable = false)
    private PositionType positionType;

    /**
     * статус сотрудника
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmployeeStatus status;

    /**
     * медицинская организация
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;

    /**
     * заблокированные вложения сотрудником
     */
    @OneToMany(mappedBy = "locker", fetch = FetchType.LAZY)
    private List<Attachment> lockedAttachments;

    /**
     * созданные вложения сотрудником
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    /**
     * загруженные версии вложения сотрудником
     */
    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
    private List<AttachmentVersion> updatedVersions;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setCreator(this);
    }

    public void addAttachmentVersion(AttachmentVersion attachmentVersion) {
        if (updatedVersions == null) {
            updatedVersions = new ArrayList<>();
        }
        updatedVersions.add(attachmentVersion);
        attachmentVersion.setUpdater(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(employeeId);
    }
}
