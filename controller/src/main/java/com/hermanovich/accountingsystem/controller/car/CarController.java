package com.hermanovich.accountingsystem.controller.car;

import com.hermanovich.accountingsystem.controller.util.CarConverter;
import com.hermanovich.accountingsystem.controller.util.OrderConverter;
import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.car.CarDtoStatus;
import com.hermanovich.accountingsystem.dto.order.OrderDto;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.service.car.CarService;
import com.hermanovich.accountingsystem.service.sortertype.CarSorterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addCar(@RequestBody CarDto carDto) {
        carService.addCar(CarConverter.convertCarDtoToCar(carDto));
        log.info("Car added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeCar(@PathVariable Integer id) {
        carService.removeCar(id);
        log.info("Car deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCar(@RequestBody CarDto carDto) {
        carService.updateCar(CarConverter.convertCarDtoToCar(carDto));
        log.info("Car updated");
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> changeCarStatus(@PathVariable Integer id,
                                                @RequestParam(name = "status") CarDtoStatus carDtoStatus) {
        carService.changeCarStatus(id, CarStatus.getStatus(carDtoStatus.name()));
        log.info("Order status changed");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable Integer id) {
        return CarConverter.convertCarToCarDto(carService.getCar(id));
    }

    @GetMapping
    public List<CarDto> getCarList(@RequestParam(name = "type", required = false) CarSorterType carSorterType,
                                   @RequestParam(name = "status", required = false) CarStatus status) {
        if (carSorterType == null) {
            return CarConverter.convertCarListToCarDtoList(carService.getCarList());
        }
        if (status == null) {
            return CarConverter.convertCarListToCarDtoList(carService.getSortedCarList(carSorterType));
        }
        return CarConverter.convertCarListToCarDtoList(carService.getSortedCarListByStatus(carSorterType, status));
    }

    @GetMapping("/{id}/countOrders")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer getCountOrdersByCarId(@PathVariable Integer id) {
        return carService.getCountOrdersByCarId(id);
    }

    @GetMapping("/countCars")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer getCountCarsByStatus(@RequestParam(name = "status", required = false) CarStatus carStatus) {
        if (carStatus == null) {
            return carService.getCountCars();
        }
        return carService.getCountCarsByStatus(carStatus);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getOrdersByCarId(@PathVariable Integer id) {
        return OrderConverter.convertOrderListToOrderDtoList(carService.getOrdersByCarId(id));
    }
}
