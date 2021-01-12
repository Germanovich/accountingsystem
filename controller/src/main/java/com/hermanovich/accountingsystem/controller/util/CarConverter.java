package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.car.CarDtoStatus;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class CarConverter {

    public CarDto convertCarToCarDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .catalog(CatalogConverter.convertCatalogToCatalogDto(car.getCatalog()))
                .firm(FirmConverter.convertFirmToFirmDto(car.getFirm()))
                .model(car.getModel())
                .carStatus(CarDtoStatus.getStatus(car.getCarStatus().name()))
                .price(car.getPrice())
                .description(car.getDescription())
                .build();
    }

    public Car convertCarDtoToCar(CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .catalog(carDto.getCatalog() == null ?
                        null : CatalogConverter.convertCatalogDtoToCatalog(carDto.getCatalog()))
                .firm(carDto.getFirm() == null ?
                        null : FirmConverter.convertFirmDtoToFirm(carDto.getFirm()))
                .model(carDto.getModel())
                .carStatus(CarStatus.getStatus(carDto.getCarStatus().name()))
                .price(carDto.getPrice())
                .description(carDto.getDescription())
                .build();
    }

    public List<CarDto> convertCarListToCarDtoList(List<Car> carList) {
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car : carList) {
            carDtoList.add(convertCarToCarDto(car));
        }
        return carDtoList;
    }
}
