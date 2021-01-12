package com.hermanovich.accountingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AEntity)) {
            return false;
        }
        AEntity aEntity = (AEntity) o;
        return getId().equals(aEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
