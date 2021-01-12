package com.hermanovich.accountingsystem.dao.catalog;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.catalog.Catalog_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CatalogDaoImpl extends HibernateAbstractDao<Catalog, Integer> implements CatalogDao {

    @Override
    protected void updateSet(CriteriaUpdate<Catalog> criteriaUpdate, Catalog catalog) {
        criteriaUpdate.set(Catalog_.name, catalog.getName());
        if (catalog.getParentCatalog().getId() == null) {
            criteriaUpdate.set(Catalog_.PARENT_CATALOG, null);
        } else {
            criteriaUpdate.set(Catalog_.PARENT_CATALOG, catalog.getParentCatalog().getId());
        }
    }

    @Override
    protected Class<Catalog> getType() {
        return Catalog.class;
    }

    @Override
    public List<Catalog> getListByParentCatalogId(Integer parentCatalogId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Catalog> criteriaQuery = criteriaBuilder.createQuery(Catalog.class);
        Root<Catalog> root = criteriaQuery.from(Catalog.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Catalog_.parentCatalog), parentCatalogId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
