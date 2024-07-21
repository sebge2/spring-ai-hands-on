package be.sgerard.springai.model.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "document")
@Table(name = "document")
@Getter
@Setter
@Accessors(chain = true)
public class DocumentEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "file_name", nullable = false, columnDefinition = "varchar(255)")
    private String fileName;

    @Column(name = "number_pages", nullable = false, columnDefinition = "integer")
    private int numberPages;

    public DocumentEntity() {
    }

    public DocumentEntity(String fileName) {
        this.fileName = fileName;
    }
}
