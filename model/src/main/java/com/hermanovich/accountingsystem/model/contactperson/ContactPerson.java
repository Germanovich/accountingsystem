package com.hermanovich.accountingsystem.model.contactperson;

import com.hermanovich.accountingsystem.model.AEntity;
import com.hermanovich.accountingsystem.model.firm.Firm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contact_persons")
public class ContactPerson extends AEntity {

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "firm_id", referencedColumnName = "id", updatable = false)
    private Firm firm;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "name")
    private String name;
}
