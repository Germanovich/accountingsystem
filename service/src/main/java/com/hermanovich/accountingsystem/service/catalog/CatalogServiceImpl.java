package com.hermanovich.accountingsystem.service.catalog;

import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.catalog.CatalogDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogDao catalogDao;
    private final CarDao carDao;

    @Transactional
    @Override
    public void addCatalog(Catalog catalog) {
        verifyCatalogData(catalog);
        if (Boolean.TRUE.equals(isParentCatalogNotExists(catalog.getParentCatalog()))) {
            catalog.setParentCatalog(null);
            catalogDao.save(catalog);
            return;
        }
        catalog.setParentCatalog(catalogDao.getById(catalog.getParentCatalog().getId()));
        catalogDao.save(catalog);
    }

    @Transactional
    @Override
    public void removeCatalog(Integer id) {
        if (catalogDao.getListByParentCatalogId(id).isEmpty() && carDao.getListByCatalogId(id).isEmpty()) {
            catalogDao.delete(id);
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_CATALOG_HAS_CHILD_CATALOGS_OR_CARS.get());
    }

    @Transactional
    @Override
    public void updateCatalog(Catalog catalog) {
        if (catalog.getId() == null || catalogDao.getById(catalog.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CATALOG_EXISTS.get());
        }
        verifyCatalogData(catalog);
        if (Boolean.TRUE.equals(isParentCatalogNotExists(catalog.getParentCatalog()))) {
            catalog.setParentCatalog(null);
            catalogDao.update(catalog);
            return;
        }
        catalog.setParentCatalog(catalogDao.getById(catalog.getParentCatalog().getId()));
        catalogDao.update(catalog);
    }

    @Transactional
    @Override
    public Catalog getCatalog(Integer id) {
        Catalog catalog = catalogDao.getById(id);
        if (catalog == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CATALOG_EXISTS.get());
        }
        return catalog;
    }

    @Transactional
    @Override
    public List<Catalog> getList() {
        return catalogDao.getAll();
    }

    @Transactional
    @Override
    public List<Catalog> getListByParentCatalogId(Integer id) {
        if (catalogDao.getById(id) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CATALOG_EXISTS.get());
        }
        return catalogDao.getListByParentCatalogId(id);
    }

    @Transactional
    @Override
    public List<Car> getCarListByCatalogId(Integer catalogId) {
        if (catalogDao.getById(catalogId) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CATALOG_EXISTS.get());
        }
        return carDao.getListByCatalogId(catalogId);
    }

    private void verifyCatalogData(Catalog catalog) {
        if (catalog.getName() == null || catalog.getName().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_CATALOG_NAME_IS_EMPTY.get());
        }
    }

    private Boolean isParentCatalogNotExists(Catalog parentCatalog) {
        if (parentCatalog == null ||
                parentCatalog.getId() == null ||
                catalogDao.getById(parentCatalog.getId()) == null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
