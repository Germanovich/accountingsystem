package com.hermanovich.accountingsystem.service.car;

import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.service.sortertype.CarSorterType;

import java.util.List;

public interface CarService {

    void addCar(Car car);

    void removeCar(Integer id);

    void updateCar(Car car);

    void changeCarStatus(Integer id, CarStatus carStatus);

    Car getCar(Integer id);

    List<Car> getCarList();

    List<Car> getSortedCarList(CarSorterType sorterType);

    List<Car> getSortedCarListByStatus(CarSorterType sorterType, CarStatus status);

    Integer getCountOrdersByCarId(Integer carId);

    Integer getCountCars();

    Integer getCountCarsByStatus(CarStatus carStatus);

    List<Order> getOrdersByCarId(Integer id);
}
