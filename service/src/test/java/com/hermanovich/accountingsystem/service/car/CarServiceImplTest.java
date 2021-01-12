package com.hermanovich.accountingsystem.service.car;

import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.catalog.CatalogDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.service.sortertype.CarSorterType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    CarServiceImpl carService;
    @Mock
    CarDao carDao;
    @Mock
    CatalogDao catalogDao;
    @Mock
    FirmDao firmDao;
    @Mock
    RequestDao requestDao;
    @Mock
    OrderDao orderDao;

    @Test
    void CarServiceImpl_addCar() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(catalog).when(catalogDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());

        carService.addCar(car);

        Mockito.verify(carDao).save(car);
    }

    @Test
    void CarServiceImpl_addCar_whenCatalogNotFoundToThrowBusinessException() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(null).when(catalogDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.addCar(car));
    }

    @Test()
    void CarServiceImpl_removeCar() {
        List<Order> orderList = new ArrayList<>();
        List<Request> requestList = new ArrayList<>();
        Integer carId = 1;

        Mockito.doReturn(requestList).when(requestDao).getListByCarId(ArgumentMatchers.anyInt());
        Mockito.doReturn(orderList).when(orderDao).getListByCarId(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(carDao).delete(ArgumentMatchers.any());

        carService.removeCar(carId);
        Mockito.verify(carDao).delete(carId);
    }

    @Test()
    void CarServiceImpl_removeCar_whenCarHasRequestsToThrowBusinessException() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        Integer carId = 1;

        Mockito.doReturn(orderList).when(orderDao).getListByCarId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.removeCar(carId));
    }

    @Test()
    void CarServiceImpl_removeCar_whenCarHasOrdersToThrowBusinessException() {
        List<Request> requestList = new ArrayList<>();
        requestList.add(Request.builder().build());
        Integer carId = 1;

        Mockito.doReturn(requestList).when(requestDao).getListByCarId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.removeCar(carId));
    }

    @Test
    void CarServiceImpl_updateCar() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(catalog).when(catalogDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());

        carService.updateCar(car);

        Mockito.verify(carDao).update(car);
    }

    @Test
    void CarServiceImpl_updateCar_whenCarNotFoundToThrowBusinessException() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.updateCar(car));
    }

    @Test
    void CarServiceImpl_updateCar_whenCatalogNotFoundToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).name("Car").build())
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(null).when(catalogDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.updateCar(car));
    }

    @Test
    void CarServiceImpl_updateCar_whenFirmNotFoundToThrowBusinessException() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm( Firm.builder().id(1).name("BMW").build())
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(catalog).when(catalogDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(null).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.updateCar(car));
    }

    @Test
    void CarServiceImpl_changeCarStatus() {
        CarStatus carStatus = CarStatus.AVAILABLE;
        Integer carId = 1;
        Car car = Car.builder()
                .id(1)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());

        carService.changeCarStatus(carId, carStatus);

        Mockito.verify(carDao).update(car);
        Mockito.verify(requestDao).deleteByCarId(car.getId());
    }

    @Test
    void CarServiceImpl_changeCarStatus_whenCarNotFoundToThrowBusinessException() {
        Integer carId = 1;

        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> carService.changeCarStatus(carId, CarStatus.AVAILABLE));
    }

    @Test
    void CarServiceImpl_changeCarStatus_whenStatusNotSetToThrowBusinessException() {
        Integer carId = 1;

        Assertions.assertThrows(BusinessException.class,
                () -> carService.changeCarStatus(carId, null));
    }

    @Test
    void CarServiceImpl_getCar() {
        Integer carId = 1;
        Car car = Car.builder()
                .id(1)
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());

        carService.getCar(carId);

        Mockito.verify(carDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void CarServiceImpl_getCarList() {
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder()
                .id(1)
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("177"))
                .build());
        carList.add(Car.builder()
                .id(2)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("137"))
                .build());
        carList.add(Car.builder()
                .id(3)
                .carStatus(CarStatus.AVAILABLE)
                .model("X9")
                .price(new BigDecimal("187"))
                .build());

        Mockito.doReturn(carList).when(carDao).getAll();

        List<Car> cars = carService.getCarList();

        Assertions.assertNotNull(cars);
        for (Car car : cars) {
            Assertions.assertNotNull(car);
        }
        Mockito.verify(carDao).getAll();
    }

    @Test
    void CarServiceImpl_getSortedCarList() {
        List<Car> carList = new ArrayList<>();
        CarSorterType carSorterType = CarSorterType.SORT_CAR_BY_PRICE;
        carList.add(Car.builder()
                .id(1)
                .catalog(Catalog.builder().build())
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("198"))
                .build());
        carList.add(Car.builder()
                .id(2)
                .catalog(Catalog.builder().build())
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("144"))
                .build());
        carList.add(Car.builder()
                .id(3)
                .catalog(Catalog.builder().build())
                .carStatus(CarStatus.AVAILABLE)
                .model("X9")
                .price(new BigDecimal("123"))
                .build());

        Mockito.doReturn(carList).when(carDao).getListByOrderByPrice();

        List<Car> cars = carService.getSortedCarList(carSorterType);

        Assertions.assertNotNull(cars);
        for (Car car : cars) {
            Assertions.assertNotNull(car.getCatalog());
        }

        Mockito.verify(carDao).getListByOrderByPrice();
    }

    @Test
    void CarServiceImpl_getCountOrdersByCarId() {
        Integer count = 3;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());

        Mockito.doReturn(orderList).when(orderDao).getListByCarId(ArgumentMatchers.anyInt());

        Integer countOrders = carService.getCountOrdersByCarId(ArgumentMatchers.anyInt());

        Assertions.assertEquals(countOrders, count);
    }

    @Test
    void CarServiceImpl_getCountCars() {
        Integer count = 4;
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());

        Mockito.doReturn(carList).when(carDao).getAll();

        Integer countCars = carService.getCountCars();

        Assertions.assertEquals(countCars, count);
    }

    @Test
    void CarServiceImpl_getCountCarsByStatus() {
        Integer count = 4;
        CarStatus carStatus = CarStatus.AVAILABLE;
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());
        carList.add(Car.builder().build());

        Mockito.doReturn(carList).when(carDao).getListByStatus(carStatus);

        Integer countCars = carService.getCountCarsByStatus(carStatus);

        Assertions.assertEquals(countCars, count);
    }

    @Test
    void CarServiceImpl_getOrdersByCarId() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());

        Mockito.doReturn(orderList).when(orderDao).getListByCarId(ArgumentMatchers.anyInt());

        List<Order> orders = carService.getOrdersByCarId(ArgumentMatchers.anyInt());

        Assertions.assertNotNull(orders);

        for (Order order : orders) {
            Assertions.assertNotNull(order);
        }
    }
}