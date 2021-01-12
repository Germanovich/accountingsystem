package com.hermanovich.accountingsystem.dao.firm;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.firm.Firm_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class FirmDaoImpl extends HibernateAbstractDao<Firm, Integer> implements FirmDao {

    @Override
    protected void updateSet(CriteriaUpdate<Firm> criteriaUpdate, Firm firm) {
        criteriaUpdate.set(Firm_.name, firm.getName());
    }

    @Override
    protected Class<Firm> getType() {
        return Firm.class;
    }

    @Override
    public List<Firm> getListByOrderByName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Firm> criteriaQuery = criteriaBuilder.createQuery(Firm.class);
        Root<Firm> root = criteriaQuery.from(Firm.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Firm_.name)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
