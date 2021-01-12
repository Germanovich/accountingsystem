package com.hermanovich.accountingsystem.dto.order;

import com.hermanovich.accountingsystem.dto.ADto;
import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends ADto {

    private UserDetailDto user;
    private CarDto car;
    private Date startDate;
    private Date endDate;
    private BigDecimal price;
    private Date actualEndDate;
}
