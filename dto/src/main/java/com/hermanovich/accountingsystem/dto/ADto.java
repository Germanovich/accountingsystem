package com.hermanovich.accountingsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class ADto {

    protected Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ADto)) {
            return false;
        }
        ADto aDto = (ADto) o;
        return getId().equals(aDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
