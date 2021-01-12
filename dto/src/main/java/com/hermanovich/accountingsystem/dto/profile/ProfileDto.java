package com.hermanovich.accountingsystem.dto.profile;

import com.hermanovich.accountingsystem.dto.ADto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends ADto {

    private String name;
    private String surname;
    private Boolean sex;
    private Date dateOfBirth;
    private Date dateOfRegistration;
}
