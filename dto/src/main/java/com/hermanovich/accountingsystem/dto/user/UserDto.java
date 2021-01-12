package com.hermanovich.accountingsystem.dto.user;

import com.hermanovich.accountingsystem.dto.ADto;
import com.hermanovich.accountingsystem.dto.profile.ProfileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ADto {

    private String login;
    private String password;
    private String role;
    private ProfileDto profile;
}
