package com.hclc.nrgyinvoicr.backend.readings;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Import extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "import_id_seq", sequenceName = "import_id_seq", initialValue = 1, allocationSize = 50)
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
    private Date readingDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Embedded
    private ReadingsSpread readingsSpread;

    public Import() {
    }

    public Import(@NotNull String fileName, @NotNull Date readingDate, @NotNull Meter meter) {
        this.importDate = new Date();
        this.fileName = fileName;
        this.readingDate = readingDate;
        this.meter = meter;
    }

    public void updateWithReadingsPeriod(Date readingsSinceClosed, Date readingsUntilOpen, long numberOfImportedReadings) {
        this.readingsSpread = new ReadingsSpread(readingsSinceClosed, readingsUntilOpen, numberOfImportedReadings);
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getImportDate() {
        return importDate;
    }

    public ReadingsSpread getReadingsSpread() {
        return readingsSpread;
    }
}
