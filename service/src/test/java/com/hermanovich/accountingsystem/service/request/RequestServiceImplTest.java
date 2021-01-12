package com.hermanovich.accountingsystem.service.request;

import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.model.user.User;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @InjectMocks
    RequestServiceImpl requestService;
    @Mock
    RequestDao requestDao;
    @Mock
    CarDao carDao;
    @Mock
    UserDao userDao;
    @Mock
    SettingDao settingDao;

    @Test
    void RequestServiceImpl_addRequest() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(requestDao).save(request);

        requestService.addRequest(request);

        Mockito.verify(requestDao).save(request);
    }

    @Test
    void RequestServiceImpl_addRequest_whenNoCarInRequestToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .build();
        Request request = Request.builder()
                .user(user)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.addRequest(request));
    }

    @Test
    void RequestServiceImpl_addRequest_whenNoUserInRequestToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        Request request = Request.builder()
                .car(car)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.addRequest(request));
    }

    @Test
    void RequestServiceImpl_addRequest_whenCarNotFoundToThrowBusinessException() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.addRequest(request));
    }

    @Test
    void RequestServiceImpl_addRequest_whenRequestCreationBlockedToThrowBusinessException() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();
        Setting setting = Setting.builder().access(Boolean.FALSE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.addRequest(request));
    }

    @Test
    void RequestServiceImpl_addRequest_whenCarAvailableToThrowBusinessException() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(requestService,
                "configurationNameForCreationRequest",
                "requestFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.addRequest(request));
    }

    @Test
    void RequestServiceImpl_removeRequest() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(requestDao).delete(ArgumentMatchers.anyInt());

        requestService.removeRequest(ArgumentMatchers.anyInt());

        Mockito.verify(requestDao).delete(ArgumentMatchers.anyInt());
    }

    @Test
    void RequestServiceImpl_removeRequest_whenRequestNotFoundToThrowBusinessException() {
        Integer requestId = 1;
        Mockito.doReturn(null).when(requestDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.removeRequest(requestId));
    }

    @Test
    void RequestServiceImpl_updateRequest() {
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
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(requestDao).update(request);

        requestService.updateRequest(request);

        Mockito.verify(requestDao).update(request);
    }

    @Test
    void RequestServiceImpl_updateRequest_whenNoCarInOrderToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .build();
        Request request = Request.builder()
                .id(1)
                .user(user)
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_updateRequest_whenNoUserInOrderToThrowBusinessException() {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        Request request = Request.builder()
                .id(1)
                .car(car)
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_updateRequest_whenRequestNotFoundToThrowBusinessException() {
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
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(null).when(requestDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_updateRequest_whenUserNotFoundToThrowBusinessException() {
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
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_updateRequest_whenCarNotFoundToThrowBusinessException() {
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
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doReturn(null).when(carDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_updateRequest_whenCarAvailableToThrowBusinessException() {
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
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(car).when(carDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> requestService.updateRequest(request));
    }

    @Test
    void RequestServiceImpl_getRequest() {
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
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestDao).getById(ArgumentMatchers.anyInt());

        requestService.getRequest(ArgumentMatchers.anyInt());

        Mockito.verify(requestDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void RequestServiceImpl_getRequestList() {
        List<Request> requestList = new ArrayList<>();
        requestList.add(Request.builder().build());
        requestList.add(Request.builder().build());
        requestList.add(Request.builder().build());

        Mockito.doReturn(requestList).when(requestDao).getAll();

        requestService.getRequestList();

        Mockito.verify(requestDao).getAll();
    }
}