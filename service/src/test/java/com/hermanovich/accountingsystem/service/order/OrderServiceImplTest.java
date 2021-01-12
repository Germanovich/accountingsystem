package com.hermanovich.accountingsystem.service.order;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.service.sortertype.OrderSorterType;
import com.hermanovich.accountingsystem.util.DataManagerSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    CarDao carDao;
    @Mock
    OrderDao orderDao;
    @Mock
    UserDao userDao;
    @Mock
    RequestDao requestDao;
    @Mock
    SettingDao settingDao;

    @Test
    void OrderServiceImpl_addOrder() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        ReflectionTestUtils.setField(orderService,
                "minNumberDays",
                1);
        ReflectionTestUtils.setField(orderService,
                "maxNumberDays",
                20);

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(carDao).update(car);
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(orderDao).save(order);

        orderService.addOrder(order, amountOfDays);

        Mockito.verify(orderDao).save(order);

    }

    @Test
    void OrderServiceImpl_addOrder_whenNoCarInOrderToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenNoUserInOrderToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        Order order = Order.builder()
                .car(car)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenCarNotFoundToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenOrderCreationBlockedToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.FALSE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenUserHasOrderToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doReturn(true).when(orderDao).isUserHaveOrder(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenCarMissingToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 2;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenAmountOfDaysLessThanOneToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 0;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_addOrder_whenAmountOfDaysMoreThanTwentyToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();
        Integer amountOfDays = 21;
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(orderService,
                "configurationNameForCreationOrder",
                "orderFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.addOrder(order, amountOfDays));
    }

    @Test
    void OrderServiceImpl_removeOrder() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(orderDao).delete(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(carDao).update(ArgumentMatchers.any());
        Mockito.doNothing().when(requestDao).deleteByCarId(ArgumentMatchers.anyInt());

        orderService.removeOrder(ArgumentMatchers.anyInt());

        Mockito.verify(orderDao).delete(ArgumentMatchers.anyInt());
    }

    @Test
    void OrderServiceImpl_removeOrder_whenOrderNotFoundToThrowBusinessException() {
        Integer orderId = 1;
        Mockito.doReturn(null).when(orderDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.removeOrder(orderId));
    }

    @Test
    void OrderServiceImpl_closeOrder() {
        String login = "user";
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .endDate(new Date())
                .price(new BigDecimal("144"))
                .build();

        ReflectionTestUtils.setField(orderService,
                "penaltyRate",
                1.5);

        Mockito.doReturn(order).when(orderDao).getValidOrderByUserId(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(carDao).update(ArgumentMatchers.any());
        Mockito.doNothing().when(requestDao).deleteByCarId(ArgumentMatchers.anyInt());

        orderService.closeOrder(login);

        Mockito.verify(requestDao).deleteByCarId(ArgumentMatchers.anyInt());
    }

    @Test
    void OrderServiceImpl_closeOrder_whenOrderNotFoundToThrowBusinessException() {
        String login = "user";
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Mockito.doReturn(null).when(orderDao).getValidOrderByUserId(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.closeOrder(login));
    }

    @Test
    void OrderServiceImpl_updateOrder() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .model("BMW")
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(1))
                .price(new BigDecimal("400"))
                .build();

        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(carDao).update(car);
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(orderDao).update(order);

        orderService.updateOrder(order);

        Mockito.verify(orderDao).update(order);
    }

    @Test
    void OrderServiceImpl_updateOrder_whenOrderNotFoundToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(null).when(orderDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_updateOrder_whenNoCarInOrderToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .user(user)
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_updateOrder_whenNoUserInOrderToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        Order order = Order.builder()
                .car(car)
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_updateOrder_whenUserNotFoundToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(2))
                .build();

        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_updateOrder_whenCarNotFoundToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_updateOrder_whenCarMissingToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(2))
                .build();

        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> orderService.updateOrder(order));
    }

    @Test
    void OrderServiceImpl_getOrder() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(order).when(orderDao).getById(ArgumentMatchers.anyInt());

        orderService.getOrder(ArgumentMatchers.anyInt());

        Mockito.verify(orderDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void OrderServiceImpl_getOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());
        orderList.add(Order.builder().build());

        Mockito.doReturn(orderList).when(orderDao).getAll();

        orderService.getOrderList();

        Mockito.verify(orderDao).getAll();
    }

    @Test
    void OrderServiceImpl_getNotExpiredOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("20-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("18-08-2020 (00:00:00:000)"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("20-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("18-08-2020 (00:00:00:000)"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("20-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("18-08-2020 (00:00:00:000)"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getNotExpiredOrderList();

        List<Order> orders = orderService.getNotExpiredOrderList();
        Assertions.assertNotNull(orders);

        for (Order order : orders) {
            Assertions.assertNotNull(order);
            Assertions.assertTrue(order.getEndDate().after(order.getActualEndDate()));
        }

        Mockito.verify(orderDao).getNotExpiredOrderList();
    }

    @Test
    void OrderServiceImpl_getExpiredOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("22-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("11-08-2020 (00:00:00:000)"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("15-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("11-08-2020 (00:00:00:000)"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("20-08-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("11-08-2020 (00:00:00:000)"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getExpiredOrderList();

        List<Order> orders = orderService.getExpiredOrderList();
        Assertions.assertNotNull(orders);

        for (Order order : orders) {
            Assertions.assertNotNull(order);
            Assertions.assertTrue(order.getEndDate().after(order.getActualEndDate()));
        }

        Mockito.verify(orderDao).getExpiredOrderList();
    }

    @Test
    void OrderServiceImpl_getAmountMoney() {
        Integer days = 10;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(3))
                .price(new BigDecimal("500"))
                .build());
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(2))
                .price(new BigDecimal("300"))
                .build());
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(1))
                .price(new BigDecimal("200"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getNotExpiredOrderList();

        BigDecimal money = orderService.getAmountMoney(days);
        Assertions.assertEquals(money, new BigDecimal("1000"));
    }

    @Test
    void OrderServiceImpl_getSortedOrderList() {
        OrderSorterType sorterType = OrderSorterType.SORT_ORDER_BY_PRICE;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("200"))
                .build());
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("300"))
                .build());
        orderList.add(Order.builder()
                .startDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("500"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getListByOrderByPrice();

        List<Order> orders = orderService.getSortedOrderList(sorterType);

        Assertions.assertNotNull(orders);
        for (Order order : orders) {
            Assertions.assertNotNull(order);
        }

        Mockito.verify(orderDao).getListByOrderByPrice();
    }

    @Test
    void OrderServiceImpl_getSortedExpiredOrderList() {
        OrderSorterType sorterType = OrderSorterType.SORT_ORDER_BY_PRICE;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("200"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("300"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("500"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getExpiredOrderListByOrderByPrice();

        List<Order> orders = orderService.getSortedExpiredOrderList(sorterType);

        Assertions.assertNotNull(orders);
        for (Order order : orders) {
            Assertions.assertNotNull(order);
            Assertions.assertTrue(order.getEndDate().before(order.getActualEndDate()));
        }

        Mockito.verify(orderDao).getExpiredOrderListByOrderByPrice();
    }

    @Test
    void OrderServiceImpl_getSortedNotExpiredOrderList() {
        OrderSorterType sorterType = OrderSorterType.SORT_ORDER_BY_PRICE;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("200"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("300"))
                .build());
        orderList.add(Order.builder()
                .endDate(DataManagerSystem.parseDate("23-11-2020 (00:00:00:000)"))
                .actualEndDate(DataManagerSystem.parseDate("22-11-2020 (00:00:00:000)"))
                .price(new BigDecimal("500"))
                .build());

        Mockito.doReturn(orderList).when(orderDao).getExpiredOrderListByOrderByPrice();

        List<Order> orders = orderService.getSortedExpiredOrderList(sorterType);

        Assertions.assertNotNull(orders);
        for (Order order : orders) {
            Assertions.assertNotNull(order);
            Assertions.assertTrue(order.getEndDate().after(order.getActualEndDate()));
        }

        Mockito.verify(orderDao).getExpiredOrderListByOrderByPrice();
    }
}