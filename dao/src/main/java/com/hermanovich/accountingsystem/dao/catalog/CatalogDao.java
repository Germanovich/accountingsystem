package com.hermanovich.accountingsystem.dao.catalog;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.catalog.Catalog;

import java.util.List;

public interface CatalogDao extends GenericDao<Catalog, Integer> {

    List<Catalog> getListByParentCatalogId(Integer parentCatalogId);
}
