package com.hermanovich.accountingsystem.dto.contactperson;

import com.hermanovich.accountingsystem.dto.ADto;
import com.hermanovich.accountingsystem.dto.firm.FirmDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactPersonDto extends ADto {

    private FirmDto firm;
    private String telephone;
    private String name;
}
