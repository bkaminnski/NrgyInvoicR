package com.softwarewithpassion.nrgyinvoicr.backend.users.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.AuditableEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "auser")
public class User extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "auser_id_seq", sequenceName = "auser_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "auser_id_seq")
    private Long id;

    @NotNull
    @Column(length = 36)
    private String externalId;

    @NotNull
    @Column(length = 36)
    private String email;

    @NotNull
    @Column(length = 36)
    private String firstName;

    @NotNull
    @Column(length = 36)
    private String lastName;

    @NotNull
    @Column(length = 60)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean matches(String password, BCryptPasswordEncoder encoder) {
        return encoder.matches(password, this.password);
    }
}
