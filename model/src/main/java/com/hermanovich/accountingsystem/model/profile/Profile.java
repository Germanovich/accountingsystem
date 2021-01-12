package com.hermanovich.accountingsystem.model.profile;

import com.hermanovich.accountingsystem.model.AEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "profiles")
public class Profile extends AEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "sex")
    private Boolean sex;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "date_of_registration")
    private Date dateOfRegistration;
}
