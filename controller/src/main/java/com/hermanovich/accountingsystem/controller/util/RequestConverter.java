package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.request.RequestDto;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.user.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class RequestConverter {


    public RequestDto convertRequestToRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .user(UserConverter.convertUserToUserDetailDto(request.getUser()))
                .car(CarConverter.convertCarToCarDto(request.getCar()))
                .build();
    }

    public Request convertRequestDtoToRequest(RequestDto requestDto) {
        if (requestDto.getUser() == null || requestDto.getUser().getLogin() == null) {
            requestDto.setUser(null);
        }
        if (requestDto.getCar() == null || requestDto.getCar().getId() == null) {
            requestDto.setCar(null);
        }
        return Request.builder()
                .id(requestDto.getId())
                .user(requestDto.getUser() == null ?
                        null : User.builder().login(requestDto.getUser().getLogin()).build())
                .car(requestDto.getCar() == null ?
                        null : Car.builder().id(requestDto.getCar().getId()).build())
                .build();
    }

    public List<RequestDto> convertRequestListToRequestDtoList(List<Request> requestList) {
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtoList.add(convertRequestToRequestDto(request));
        }
        return requestDtoList;
    }
}
