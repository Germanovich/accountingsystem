package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.order.OrderDto;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.user.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class OrderConverter {

    public OrderDto convertOrderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .user(UserConverter.convertUserToUserDetailDto(order.getUser()))
                .car(CarConverter.convertCarToCarDto(order.getCar()))
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .actualEndDate(order.getActualEndDate())
                .build();
    }

    public Order convertOrderDtoToOrder(OrderDto orderDto) {
        if (orderDto.getUser() == null || orderDto.getUser().getLogin() == null) {
            orderDto.setUser(null);
        }
        if (orderDto.getCar() == null || orderDto.getCar().getId() == null) {
            orderDto.setCar(null);
        }
        return Order.builder()
                .id(orderDto.getId())
                .user(orderDto.getUser() == null ?
                        null : User.builder().login(orderDto.getUser().getLogin()).build())
                .car(orderDto.getCar() == null ?
                        null : Car.builder().id(orderDto.getCar().getId()).build())
                .startDate(orderDto.getStartDate())
                .endDate(orderDto.getEndDate())
                .price(orderDto.getPrice())
                .actualEndDate(orderDto.getActualEndDate())
                .build();
    }

    public List<OrderDto> convertOrderListToOrderDtoList(List<Order> orderList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoList.add(convertOrderToOrderDto(order));
        }
        return orderDtoList;
    }
}
