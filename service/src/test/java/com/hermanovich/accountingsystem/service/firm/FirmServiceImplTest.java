package com.hermanovich.accountingsystem.service.firm;

import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.contactperson.ContactPersonDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.sortertype.FirmSorterType;
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
class FirmServiceImplTest {

    @InjectMocks
    FirmServiceImpl firmService;
    @Mock
    FirmDao firmDao;
    @Mock
    CarDao carDao;
    @Mock
    ContactPersonDao contactPersonDao;

    @Test
    void FirmServiceImpl_addFirm() {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doNothing().when(firmDao).save(ArgumentMatchers.any());

        firmService.addFirm(firm);

        Mockito.verify(firmDao).save(firm);
    }

    @Test
    void FirmServiceImpl_addFirm_whenEmptyFirmNameToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .name("")
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.addFirm(firm));
    }

    @Test
    void FirmServiceImpl_removeFirm() {
        List<Car> carList = new ArrayList<>();
        List<ContactPerson> personList = new ArrayList<>();
        Integer firmId = 1;

        Mockito.doReturn(carList).when(carDao).getListByFirmId(ArgumentMatchers.anyInt());
        Mockito.doReturn(personList).when(contactPersonDao).getListByFirmId(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(firmDao).delete(ArgumentMatchers.anyInt());

        firmService.removeFirm(firmId);
        Mockito.verify(firmDao).delete(firmId);
    }

    @Test
    void FirmServiceImpl_removeFirm_whenFirmHasCarsToThrowBusinessException() {
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder().build());
        Integer firmId = 1;

        Mockito.doReturn(carList).when(carDao).getListByFirmId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.removeFirm(firmId));
    }

    @Test
    void FirmServiceImpl_removeFirm_whenFirmHasContactPersonsToThrowBusinessException() {
        List<Car> carList = new ArrayList<>();
        List<ContactPerson> personList = new ArrayList<>();
        personList.add(ContactPerson.builder().build());
        Integer firmId = 1;

        Mockito.doReturn(carList).when(carDao).getListByFirmId(ArgumentMatchers.anyInt());
        Mockito.doReturn(personList).when(contactPersonDao).getListByFirmId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.removeFirm(firmId));
    }

    @Test
    void FirmServiceImpl_updateFirm() {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(firmDao).update(ArgumentMatchers.any());

        firmService.updateFirm(firm);

        Mockito.verify(firmDao).update(firm);
    }

    @Test
    void FirmServiceImpl_updateFirm_whenNoSuchFirmExistsToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doReturn(null).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.updateFirm(firm));
    }

    @Test
    void FirmServiceImpl_updateFirm_whenFirmNameIsMissingToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .build();

        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.updateFirm(firm));
    }

    @Test
    void FirmServiceImpl_getFirm() {
        Integer firmId = 1;
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());

        firmService.getFirm(firmId);

        Mockito.verify(firmDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void FirmServiceImpl_getFirmList() {
        List<Firm> firmList = new ArrayList<>();
        firmList.add(Firm.builder()
                .id(1)
                .name("BMW")
                .build());
        firmList.add(Firm.builder()
                .id(1)
                .name("Audi")
                .build());

        Mockito.doReturn(firmList).when(firmDao).getAll();

        List<Firm> firms = firmService.getFirmList();

        Assertions.assertNotNull(firms);
        for (Firm firm : firms) {
            Assertions.assertNotNull(firm);
        }
        Mockito.verify(firmDao).getAll();
    }

    @Test
    void FirmServiceImpl_getSortedFirmList() {
        FirmSorterType sorterType = FirmSorterType.SORT_FIRM_BY_NAME;
        List<Firm> firmList = new ArrayList<>();
        firmList.add(Firm.builder()
                .id(1)
                .name("Audi")
                .build());
        firmList.add(Firm.builder()
                .id(1)
                .name("BMW")
                .build());

        Mockito.doReturn(firmList).when(firmDao).getListByOrderByName();

        List<Firm> firms = firmService.getSortedFirmList(sorterType);

        Assertions.assertNotNull(firms);
        for (Firm firm : firms) {
            Assertions.assertNotNull(firm);
        }
        Mockito.verify(firmDao).getListByOrderByName();
    }

    @Test
    void FirmServiceImpl_getContactPersonsListByFirmId() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        List<ContactPerson> contactPersonList = new ArrayList<>();
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .build());
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Tima")
                .firm(firm)
                .build());

        Mockito.doReturn(firm).when(firmDao).getById(firm.getId());
        Mockito.doReturn(contactPersonList).when(contactPersonDao).getListByFirmId(ArgumentMatchers.anyInt());

        firmService.getContactPersonsListByFirmId(firm.getId());

        Mockito.verify(contactPersonDao).getListByFirmId(firm.getId());
    }

    @Test
    void FirmServiceImpl_getContactPersonsListByFirmId_whenFirmNotFoundToThrowBusinessException() {
        Integer firmId = 1;

        Mockito.doReturn(null).when(firmDao).getById(firmId);

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.getContactPersonsListByFirmId(firmId));
    }

    @Test
    void FirmServiceImpl_getCarListByFirmId() {
        Integer firmId = 1;
        Firm firm = Firm.builder()
                .id(firmId)
                .build();
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder()
                .id(1)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("198"))
                .build());
        carList.add(Car.builder()
                .id(2)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("144"))
                .build());
        carList.add(Car.builder()
                .id(3)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X9")
                .price(new BigDecimal("123"))
                .build());

        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(carList).when(carDao).getListByFirmId(ArgumentMatchers.anyInt());

        List<Car> cars = firmService.getCarListByFirmId(firmId);

        Assertions.assertNotNull(cars);

        for (Car car : cars) {
            Assertions.assertNotNull(car.getFirm());
            Assertions.assertEquals(car.getFirm().getId(), firmId);
        }

        Mockito.verify(carDao).getListByFirmId(ArgumentMatchers.anyInt());
    }

    @Test
    void CarServiceImpl_getCarListByFirmId_whenFirmNotFoundToThrowBusinessException() {
        Integer firmId = 1;
        Mockito.doReturn(null).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> firmService.getCarListByFirmId(firmId));
    }
}