package com.hermanovich.accountingsystem.model.car;

import com.hermanovich.accountingsystem.model.AEntity;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
public class Car extends AEntity {

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id", updatable = false)
    private Catalog catalog;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "firm_id", referencedColumnName = "id", updatable = false)
    private Firm firm;
    @Column(name = "model")
    private String model;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "description")
    private String description;
}
