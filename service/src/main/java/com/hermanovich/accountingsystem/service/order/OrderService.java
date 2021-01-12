package com.hermanovich.accountingsystem.service.order;

import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.service.sortertype.OrderSorterType;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void addOrder(Order order, Integer amountOfDays);

    void removeOrder(Integer id);

    void closeOrder(String login);

    void updateOrder(Order order);

    Order getOrder(Integer id);

    List<Order> getOrderList();

    List<Order> getOrderList(String login);

    List<Order> getNotExpiredOrderList();

    List<Order> getExpiredOrderList();

    BigDecimal getAmountMoney(Integer numberOfDays);

    List<Order> getSortedOrderList(OrderSorterType sorterType);

    List<Order> getSortedExpiredOrderList(OrderSorterType sorterType);

    List<Order> getSortedNotExpiredOrderList(OrderSorterType sorterType);
}
