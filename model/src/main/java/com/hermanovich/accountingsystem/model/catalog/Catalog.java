package com.hermanovich.accountingsystem.model.catalog;

import com.hermanovich.accountingsystem.model.AEntity;
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
@Table(name = "catalogs")
public class Catalog extends AEntity {

    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_catalog_id", referencedColumnName = "id", updatable = false)
    private Catalog parentCatalog;
}
