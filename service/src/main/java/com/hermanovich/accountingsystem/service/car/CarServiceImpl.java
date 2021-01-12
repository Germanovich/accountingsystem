package com.hermanovich.accountingsystem.service.car;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.catalog.CatalogDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.service.sortertype.CarSorterType;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;
    private final CatalogDao catalogDao;
    private final FirmDao firmDao;
    private final RequestDao requestDao;
    private final OrderDao orderDao;

    @Transactional
    @Override
    public void addCar(Car car) {
        verifyCarData(car);
        car.setCatalog(catalogDao.getById(car.getCatalog().getId()));
        car.setFirm(firmDao.getById(car.getFirm().getId()));
        carDao.save(car);
    }

    @Transactional
    @Override
    public void removeCar(Integer id) {
        if (requestDao.getListByCarId(id).isEmpty() && orderDao.getListByCarId(id).isEmpty()) {
            carDao.delete(id);
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_CAR_HAS_ORDERS_OR_REQUESTS.get());
    }

    @Transactional
    @Override
    public void updateCar(Car car) {
        if (car.getId() == null || carDao.getById(car.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CAR_EXISTS.get());
        }
        verifyCarData(car);
        car.setCatalog(catalogDao.getById(car.getCatalog().getId()));
        car.setFirm(firmDao.getById(car.getFirm().getId()));
        carDao.update(car);
    }

    @Transactional
    @Override
    public void changeCarStatus(Integer id, CarStatus carStatus) {
        if (carStatus != null) {
            Car car = carDao.getById(id);
            if (car == null) {
                throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CAR_EXISTS.get());
            }
            car.setCarStatus(carStatus);
            carDao.update(car);
            if (car.getCarStatus() == CarStatus.AVAILABLE) {
                requestDao.deleteByCarId(id);
            }
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get());
    }

    @Transactional
    @Override
    public Car getCar(Integer id) {
        Car car = carDao.getById(id);
        if (car == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CAR_EXISTS.get());
        }
        return car;
    }

    @Transactional
    @Override
    public List<Car> getCarList() {
        return carDao.getAll();
    }

    @Transactional
    @Override
    public List<Car> getSortedCarList(CarSorterType sorterType) {
        if (CarSorterType.SORT_CAR_BY_MODEL.equals(sorterType)) {
            return carDao.getListByOrderByModel();
        }
        if (CarSorterType.SORT_CAR_BY_PRICE.equals(sorterType)) {
            return carDao.getListByOrderByPrice();
        }
        if (CarSorterType.SORT_CAR_BY_STATUS.equals(sorterType)) {
            return carDao.getListByOrderByStatus();
        }
        return carDao.getAll();
    }

    @Transactional
    @Override
    public List<Car> getSortedCarListByStatus(CarSorterType sorterType, CarStatus status) {
        if (CarSorterType.SORT_CAR_BY_MODEL.equals(sorterType)) {
            return carDao.getListByStatusOrderByModel(status);
        }
        if (CarSorterType.SORT_CAR_BY_PRICE.equals(sorterType)) {
            return carDao.getListByStatusOrderByPrice(status);
        }
        return carDao.getAll();
    }

    @Transactional
    @Override
    public Integer getCountOrdersByCarId(Integer carId) {
        return orderDao.getListByCarId(carId).size();
    }

    @Transactional
    @Override
    public Integer getCountCars() {
        return carDao.getAll().size();
    }

    @Transactional
    @Override
    public Integer getCountCarsByStatus(CarStatus carStatus) {
        if (carStatus == null) {
            throw new BusinessException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get());
        }
        return carDao.getListByStatus(carStatus).size();
    }

    @Transactional
    @Override
    public List<Order> getOrdersByCarId(Integer id) {
        return orderDao.getListByCarId(id);
    }

    private void verifyCarData(Car car) {
        if (car.getCatalog() == null ||
                car.getCatalog().getId() == null ||
                car.getFirm() == null ||
                car.getFirm().getId() == null ||
                car.getModel() == null ||
                car.getCarStatus() == null ||
                car.getPrice() == null ||
                car.getModel().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_CAR_DATA.get());
        }
        if (catalogDao.getById(car.getCatalog().getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CATALOG_EXISTS.get());
        }
        if (firmDao.getById(car.getFirm().getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
    }
}
