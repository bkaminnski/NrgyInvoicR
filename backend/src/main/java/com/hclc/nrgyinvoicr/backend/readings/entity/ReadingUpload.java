package com.hclc.nrgyinvoicr.backend.readings.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

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
    private ZonedDateTime date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reading_id", nullable = false)
    private Reading reading;

    public ReadingUpload() {
    }

    public ReadingUpload(@NotNull String fileName, @NotNull Reading reading) {
        this.date = ZonedDateTime.now();
        this.fileName = fileName;
        this.reading = reading;
    }
}
