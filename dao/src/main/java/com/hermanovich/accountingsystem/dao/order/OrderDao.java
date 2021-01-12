package com.hermanovich.accountingsystem.dao.order;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.order.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Integer> {

    Boolean isUserHaveOrder(Integer userId);

    Order getValidOrderByUserId(Integer userId);

    List<Order> getListByCarId(Integer carId);

    List<Order> getListByUserId(Integer userId);

    List<Order> getNotExpiredOrderList();

    List<Order> getExpiredOrderList();

    List<Order> getListByOrderByStartDate();

    List<Order> getListByOrderByEndDate();

    List<Order> getListByOrderByPrice();

    List<Order> getListByOrderByStatus();

    List<Order> getNotExpiredOrderListByOrderByStartDate();

    List<Order> getNotExpiredOrderListByOrderByEndDate();

    List<Order> getNotExpiredOrderListByOrderByPrice();

    List<Order> getNotExpiredOrderListByOrderByStatus();

    List<Order> getExpiredOrderListByOrderByStartDate();

    List<Order> getExpiredOrderListByOrderByEndDate();

    List<Order> getExpiredOrderListByOrderByPrice();

    List<Order> getExpiredOrderListByOrderByStatus();
}
