package academy.kata.mis.fileapiservice.model.entity;

import academy.kata.mis.fileapiservice.model.enums.Category;
import academy.kata.mis.fileapiservice.model.enums.DocumentStatus;
import academy.kata.mis.fileapiservice.model.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * информация о файле
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "documents")
public class Document {

    /**
     * ID генерируется тут
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя документа
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Дата и время создания
     */
    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creationDatetime;

    /**
     * Дата и время удаления
     */
    @Column(name = "expiration_datetime")
    private LocalDateTime expirationDatetime;

    /**
     * тип документа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    /**
     * вес документа
     */
    @Column(name = "size", nullable = false)
    private long size;

    /**
     * категория документа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    /**
     * статус документа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "document_status", nullable = false)
    private DocumentStatus documentStatus;

    /**
     * Владелец файла
     * user_id из authService
     */
    @Column(name = "owner_user_id")
    private UUID ownerUserId;

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
        Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
