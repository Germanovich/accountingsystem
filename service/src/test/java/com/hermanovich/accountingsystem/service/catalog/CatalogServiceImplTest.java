package com.hermanovich.accountingsystem.service.catalog;

import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.catalog.CatalogDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
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
class CatalogServiceImplTest {

    @InjectMocks
    CatalogServiceImpl catalogService;
    @Mock
    CarDao carDao;
    @Mock
    CatalogDao catalogDao;

    @Test
    void CatalogServiceImpl_addCatalog() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .parentCatalog(Catalog.builder().id(2).build())
                .build();

        Mockito.doNothing().when(catalogDao).save(ArgumentMatchers.any());
        Mockito.doReturn(null).when(catalogDao).getById(ArgumentMatchers.anyInt());

        catalogService.addCatalog(catalog);

        Mockito.verify(catalogDao).save(catalog);
    }

    @Test
    void CatalogServiceImpl_addCatalog_whenCatalogHasEmptyNameToThrowBusinessException() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.addCatalog(catalog));
    }

    @Test
    void CatalogServiceImpl_removeCatalog() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        List<Catalog> catalogList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();

        Mockito.doReturn(catalogList).when(catalogDao).getListByParentCatalogId(ArgumentMatchers.anyInt());
        Mockito.doReturn(carList).when(carDao).getListByCatalogId(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(catalogDao).delete(ArgumentMatchers.anyInt());

        catalogService.removeCatalog(catalog.getId());

        Mockito.verify(catalogDao).delete(catalog.getId());
    }

    @Test
    void CatalogServiceImpl_removeCatalog_whenCatalogHasChildCatalogToThrowBusinessException() {
        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(Catalog.builder().build());

        Mockito.doReturn(catalogList).when(catalogDao).getListByParentCatalogId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.removeCatalog(1));
    }

    @Test
    void CatalogServiceImpl_removeCatalog_whenCatalogHasCarsToThrowBusinessException() {
        List<Catalog> catalogList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder().build());

        Mockito.doReturn(catalogList).when(catalogDao).getListByParentCatalogId(ArgumentMatchers.anyInt());
        Mockito.doReturn(carList).when(carDao).getListByCatalogId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.removeCatalog(1));
    }

    @Test
    void CatalogServiceImpl_updateCatalog() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .parentCatalog(Catalog.builder().id(2).build())
                .build();

        Mockito.doNothing().when(catalogDao).update(ArgumentMatchers.any());
        Mockito.doReturn(catalog).when(catalogDao).getById(catalog.getId());
        Mockito.doReturn(null).when(catalogDao).getById(catalog.getParentCatalog().getId());

        catalogService.updateCatalog(catalog);

        Mockito.verify(catalogDao).update(catalog);
    }

    @Test
    void CatalogServiceImpl_updateCatalog_whenCatalogHasEmptyNameToThrowBusinessException() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .build();

        Mockito.doReturn(catalog).when(catalogDao).getById(catalog.getId());

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.updateCatalog(catalog));
    }

    @Test
    void CatalogServiceImpl_getCatalog() {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();

        Mockito.doReturn(catalog).when(catalogDao).getById(ArgumentMatchers.anyInt());

        catalogService.getCatalog(catalog.getId());

        Mockito.verify(catalogDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void CatalogServiceImpl_getList() {
        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Car")
                .build());
        catalogList.add(Catalog.builder()
                .id(2)
                .name("Car")
                .build());
        catalogList.add(Catalog.builder()
                .id(3)
                .name("Car")
                .build());

        Mockito.doReturn(catalogList).when(catalogDao).getAll();

        List<Catalog> catalogs = catalogService.getList();


        Assertions.assertNotNull(catalogs);
        for (Catalog catalog : catalogs) {
            Assertions.assertNotNull(catalog);
        }
        Mockito.verify(catalogDao).getAll();
    }

    @Test
    void CatalogServiceImpl_getListByParentCatalogId() {
        Catalog parentCatalog = Catalog.builder().id(7).name("Car").build();
        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Fast car")
                .parentCatalog(parentCatalog)
                .build());
        catalogList.add(Catalog.builder()
                .id(2)
                .name("Small car")
                .parentCatalog(parentCatalog)
                .build());
        catalogList.add(Catalog.builder()
                .id(3)
                .name("Big car")
                .parentCatalog(parentCatalog)
                .build());

        Mockito.doReturn(catalogList).when(catalogDao).getListByParentCatalogId(ArgumentMatchers.anyInt());
        Mockito.doReturn(parentCatalog).when(catalogDao).getById(ArgumentMatchers.anyInt());

        List<Catalog> catalogs = catalogService.getListByParentCatalogId(parentCatalog.getId());


        Assertions.assertNotNull(catalogs);
        for (Catalog catalog : catalogs) {
            Assertions.assertNotNull(catalog);
            Assertions.assertNotNull(catalog.getParentCatalog());
            Assertions.assertEquals(catalog.getParentCatalog(), parentCatalog);
        }
        Mockito.verify(catalogDao).getListByParentCatalogId(parentCatalog.getId());
    }

    @Test
    void CatalogServiceImpl_getListByParentCatalogId_whenParentCatalogNotFoundToThrowBusinessException() {
        Integer parentCatalogId = 1;

        Mockito.doReturn(null).when(catalogDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.getListByParentCatalogId(parentCatalogId));
    }

    @Test
    void CatalogServiceImpl_getCarListByCatalogId() {
        Integer catalogId = 1;
        Catalog catalog = Catalog.builder()
                .id(catalogId)
                .build();
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder()
                .id(1)
                .catalog(catalog)
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("198"))
                .build());
        carList.add(Car.builder()
                .id(2)
                .catalog(catalog)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("144"))
                .build());
        carList.add(Car.builder()
                .id(3)
                .catalog(catalog)
                .carStatus(CarStatus.AVAILABLE)
                .model("X9")
                .price(new BigDecimal("123"))
                .build());

        Mockito.doReturn(catalog).when(catalogDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(carList).when(carDao).getListByCatalogId(ArgumentMatchers.anyInt());

        List<Car> cars = catalogService.getCarListByCatalogId(catalogId);

        Assertions.assertNotNull(cars);
        for (Car car : cars) {
            Assertions.assertNotNull(car.getCatalog());
            Assertions.assertEquals(car.getCatalog().getId(), catalogId);
        }
    }

    @Test
    void CatalogServiceImpl_getCarListByCatalogId_whenCatalogNotFoundToThrowBusinessException() {
        Integer catalogId = 1;
        Mockito.doReturn(null).when(catalogDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> catalogService.getCarListByCatalogId(catalogId));
    }
}


