package com.hermanovich.accountingsystem.dto.firm;

import com.hermanovich.accountingsystem.dto.ADto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FirmDto extends ADto {

    private String name;
}
