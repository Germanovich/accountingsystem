package com.hermanovich.accountingsystem.dto.car;

import com.hermanovich.accountingsystem.dto.ADto;
import com.hermanovich.accountingsystem.dto.catalog.CatalogDto;
import com.hermanovich.accountingsystem.dto.firm.FirmDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CarDto extends ADto {

    private CatalogDto catalog;
    private FirmDto firm;
    private String model;
    private CarDtoStatus carStatus;
    private BigDecimal price;
    private String description;
}
