package com.hermanovich.accountingsystem.dto.request;

import com.hermanovich.accountingsystem.dto.ADto;
import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RequestDto extends ADto {

    private UserDetailDto user;
    private CarDto car;
}
