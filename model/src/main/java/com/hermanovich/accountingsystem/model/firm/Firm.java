package com.hermanovich.accountingsystem.model.firm;

import com.hermanovich.accountingsystem.model.AEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "firms")
public class Firm extends AEntity {

    @Column(name = "name")
    private String name;
}
