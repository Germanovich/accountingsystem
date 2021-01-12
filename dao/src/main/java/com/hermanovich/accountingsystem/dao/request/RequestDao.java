package com.hermanovich.accountingsystem.dao.request;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.request.Request;

import java.util.List;

public interface RequestDao extends GenericDao<Request, Integer> {

    List<Request> getListByCarId(Integer carId);

    List<Request> getListByUserId(Integer userId);

    Request getRequestByCarIdAndUserId(Integer carId, Integer userId);

    void deleteByCarId(Integer carId);
}
