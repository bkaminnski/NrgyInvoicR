package com.hclc.nrgyinvoicr.backend.imports;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;
import com.hclc.nrgyinvoicr.backend.meters.Meter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Import extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "import_id_seq", sequenceName = "import_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "import_id_seq")
    private Long id;

    @NotNull
    @Column(length = 255)
    private String fileName;

    @NotNull
    @Temporal(TIMESTAMP)
    private Date importDate;

    @NotNull
    @Temporal(TIMESTAMP)
    private Date readingsSinceClosed;

    @NotNull
    @Temporal(TIMESTAMP)
    private Date readingsUntilOpen;

    @ManyToOne
    @JoinColumn(name="meter_id", nullable=false)
    private Meter meter;

    public Import() {
    }

    public Import(@NotNull String fileName, @NotNull Date importDate) {
        this.fileName = fileName;
        this.importDate = importDate;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getImportDate() {
        return importDate;
    }

    public Date getReadingsSinceClosed() {
        return readingsSinceClosed;
    }

    public Date getReadingsUntilOpen() {
        return readingsUntilOpen;
    }
}
