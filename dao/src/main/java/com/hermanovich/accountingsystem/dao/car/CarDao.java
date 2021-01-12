package com.hermanovich.accountingsystem.dao.car;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;

import java.util.List;

public interface CarDao extends GenericDao<Car, Integer> {

    List<Car> getListByCatalogId(Integer catalogId);

    List<Car> getListByFirmId(Integer firmId);

    List<Car> getListByStatus(CarStatus carStatus);

    List<Car> getListByOrderByModel();

    List<Car> getListByOrderByStatus();

    List<Car> getListByOrderByPrice();

    List<Car> getListByStatusOrderByModel(CarStatus status);

    List<Car> getListByStatusOrderByPrice(CarStatus status);
}
