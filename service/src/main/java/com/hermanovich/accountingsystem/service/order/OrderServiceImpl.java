package com.hermanovich.accountingsystem.service.order;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.service.sortertype.OrderSorterType;
import com.hermanovich.accountingsystem.util.DataManagerSystem;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${setting.configurationNameForCreationOrder:orderFunctionEnabled}")
    private String configurationNameForCreationOrder;
    @Value("${minNumberDays:1}")
    private Integer minNumberDays;
    @Value("${maxNumberDays:20}")
    private Integer maxNumberDays;
    @Value("${penaltyRate:1.5}")
    private Double penaltyRate;
    private final OrderDao orderDao;
    private final CarDao carDao;
    private final UserDao userDao;
    private final RequestDao requestDao;
    private final SettingDao settingDao;

    @Transactional
    @Override
    public void addOrder(Order order, Integer amountOfDays) {
        if (Boolean.FALSE.equals(settingDao.getSettingByName(configurationNameForCreationOrder).getAccess())) {
            throw new BusinessException(MessageForUser.MESSAGE_ORDER_CREATION_IS_PROHIBITED.get());
        }
        verifyOrderData(order);
        order.setCar(carDao.getById(order.getCar().getId()));
        order.setUser(userDao.getByLogin(order.getUser().getLogin()));
        if (Boolean.TRUE.equals(orderDao.isUserHaveOrder(order.getUser().getId()))) {
            throw new BusinessException(MessageForUser.MESSAGE_USER_ALREADY_HAS_AN_ACTIVE_ORDER.get());
        }
        orderDao.save(fillNewOrder(order, amountOfDays));
        updateCarStatus(order.getCar(), CarStatus.MISSING);
    }

    @Transactional
    @Override
    public void removeOrder(Integer id) {
        Order order = orderDao.getById(id);
        if (order == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_ORDER_EXISTS.get());
        }
        orderDao.delete(id);
        updateCarStatus(order.getCar(), CarStatus.AVAILABLE);
        requestDao.deleteByCarId(order.getCar().getId());
    }

    @Transactional
    @Override
    public void closeOrder(String login) {
        Order order = orderDao.getValidOrderByUserId(userDao.getByLogin(login).getId());
        if (order == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_ORDER_EXISTS.get());
        }
        orderDao.update(fillCloseOrder(order));
        updateCarStatus(order.getCar(), CarStatus.AVAILABLE);
        requestDao.deleteByCarId(order.getCar().getId());
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        if (order.getId() == null || orderDao.getById(order.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_ORDER_EXISTS.get());
        }
        if (userDao.getByLogin(order.getUser().getLogin()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_USER_EXISTS.get());
        }
        verifyOrderData(order);
        if (order.getStartDate() == null ||
                order.getEndDate() == null ||
                order.getPrice() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_ORDER_DATA.get());
        }
        Car car = orderDao.getById(order.getId()).getCar();
        if (car.equals(order.getCar())) {
            orderDao.update(order);
            updateCarStatus(order.getCar(), CarStatus.MISSING);
            return;
        }
        orderDao.update(order);
        updateCarStatus(car, CarStatus.AVAILABLE);
        updateCarStatus(order.getCar(), CarStatus.MISSING);
    }

    @Transactional
    @Override
    public Order getOrder(Integer id) {
        Order order = orderDao.getById(id);
        if (order == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_ORDER_EXISTS.get());
        }
        return order;
    }

    @Transactional
    @Override
    public List<Order> getOrderList() {
        return orderDao.getAll();
    }

    @Transactional
    @Override
    public List<Order> getOrderList(String login) {
        return orderDao.getListByUserId(userDao.getByLogin(login).getId());
    }

    @Transactional
    @Override
    public List<Order> getNotExpiredOrderList() {
        return orderDao.getNotExpiredOrderList();
    }

    @Transactional
    @Override
    public List<Order> getExpiredOrderList() {
        return orderDao.getExpiredOrderList();
    }

    @Transactional
    @Override
    public BigDecimal getAmountMoney(Integer numberOfDays) {
        return orderDao.getNotExpiredOrderList()
                .stream()
                .filter(order -> order.getStartDate().after(DataManagerSystem.getDateDaysAgo(numberOfDays)))
                .map(Order::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    @Override
    public List<Order> getSortedOrderList(OrderSorterType sorterType) {
        if (OrderSorterType.SORT_ORDER_BY_END_DATE.equals(sorterType)) {
            return orderDao.getListByOrderByEndDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_START_DATE.equals(sorterType)) {
            return orderDao.getListByOrderByStartDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_PRICE.equals(sorterType)) {
            return orderDao.getListByOrderByPrice();
        }
        if (OrderSorterType.SORT_ORDER_BY_STATUS.equals(sorterType)) {
            return orderDao.getListByOrderByStatus();
        }
        return orderDao.getAll();
    }

    @Transactional
    @Override
    public List<Order> getSortedExpiredOrderList(OrderSorterType sorterType) {
        if (OrderSorterType.SORT_ORDER_BY_END_DATE.equals(sorterType)) {
            return orderDao.getExpiredOrderListByOrderByEndDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_START_DATE.equals(sorterType)) {
            return orderDao.getExpiredOrderListByOrderByStartDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_PRICE.equals(sorterType)) {
            return orderDao.getExpiredOrderListByOrderByPrice();
        }
        if (OrderSorterType.SORT_ORDER_BY_STATUS.equals(sorterType)) {
            return orderDao.getExpiredOrderListByOrderByStatus();
        }
        return orderDao.getAll();
    }

    @Transactional
    @Override
    public List<Order> getSortedNotExpiredOrderList(OrderSorterType sorterType) {
        if (OrderSorterType.SORT_ORDER_BY_END_DATE.equals(sorterType)) {
            return orderDao.getNotExpiredOrderListByOrderByEndDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_START_DATE.equals(sorterType)) {
            return orderDao.getNotExpiredOrderListByOrderByStartDate();
        }
        if (OrderSorterType.SORT_ORDER_BY_PRICE.equals(sorterType)) {
            return orderDao.getNotExpiredOrderListByOrderByPrice();
        }
        if (OrderSorterType.SORT_ORDER_BY_STATUS.equals(sorterType)) {
            return orderDao.getNotExpiredOrderListByOrderByStatus();
        }
        return orderDao.getAll();
    }

    private Order fillNewOrder(Order order, Integer amountOfDays) {
        if (amountOfDays < minNumberDays || amountOfDays > maxNumberDays) {
            throw new BusinessException(MessageForUser.MESSAGE_THE_NUMBER_OF_DAYS_IS_MORE_OR_LESS_THAN_ALLOWED.get());
        }
        order.setActualEndDate(null);
        order.setStartDate(new Date());
        order.setEndDate(DataManagerSystem.addDays(order.getStartDate(), amountOfDays));
        order.setPrice(order.getCar().getPrice().multiply(new BigDecimal(amountOfDays)));
        return order;
    }

    private Order fillCloseOrder(Order order) {
        order.setActualEndDate(new Date());
        if (order.getActualEndDate().after(order.getEndDate())) {
            Integer days = DataManagerSystem.getNumberOfDays(order.getEndDate(), order.getActualEndDate());
            order.setPrice(order.getPrice().add(BigDecimal.valueOf(days * penaltyRate)));
        }
        return order;
    }

    private void updateCarStatus(Car car, CarStatus status) {
        car.setCarStatus(status);
        carDao.update(car);
    }

    private void verifyOrderData(Order order) {
        if (order.getCar() == null ||
                order.getUser() == null ||
                order.getCar().getId() == null ||
                order.getUser().getLogin() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_ORDER_DATA.get());
        }
        order.setCar(carDao.getById(order.getCar().getId()));
        if (order.getCar() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CAR_EXISTS.get());
        }
        if (order.getCar().getCarStatus() == CarStatus.MISSING) {
            throw new BusinessException(MessageForUser.MESSAGE_CAR_NOT_AVAILABLE.get());
        }
    }
}
