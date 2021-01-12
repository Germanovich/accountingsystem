package com.hermanovich.accountingsystem.service.catalog;

import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.catalog.Catalog;

import java.util.List;

public interface CatalogService {

    void addCatalog(Catalog catalog);

    void removeCatalog(Integer id);

    void updateCatalog(Catalog catalog);

    Catalog getCatalog(Integer id);

    List<Catalog> getList();

    List<Catalog> getListByParentCatalogId(Integer id);

    List<Car> getCarListByCatalogId(Integer catalogId);
}
