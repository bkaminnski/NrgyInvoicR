package com.hclc.nrgyinvoicr.backend.readings.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class ReadingUpload extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "reading_upload_id_seq", sequenceName = "reading_upload_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "reading_upload_id_seq")
    private Long id;

    @NotNull
    @Column(length = 255)
    private String fileName;

    @NotNull
    @Temporal(TIMESTAMP)
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reading_id", nullable = false)
    private Reading reading;

    public ReadingUpload() {
    }

    public ReadingUpload(@NotNull String fileName, @NotNull Reading reading) {
        this.date = new Date();
        this.fileName = fileName;
        this.reading = reading;
    }
}
