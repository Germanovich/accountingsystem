package com.hermanovich.accountingsystem.dao.contactperson;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ContactPersonDaoImpl extends HibernateAbstractDao<ContactPerson, Integer> implements ContactPersonDao {

    @Override
    protected void updateSet(CriteriaUpdate<ContactPerson> criteriaUpdate, ContactPerson contactPerson) {
        criteriaUpdate.set(ContactPerson_.FIRM, contactPerson.getFirm().getId());
        criteriaUpdate.set(ContactPerson_.telephone, contactPerson.getTelephone());
        criteriaUpdate.set(ContactPerson_.name, contactPerson.getName());
    }

    @Override
    protected Class<ContactPerson> getType() {
        return ContactPerson.class;
    }

    @Override
    public List<ContactPerson> getListByFirmId(Integer firmId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactPerson> criteriaQuery = criteriaBuilder.createQuery(ContactPerson.class);
        Root<ContactPerson> root = criteriaQuery.from(ContactPerson.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(ContactPerson_.firm), firmId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<ContactPerson> getListByOrderByName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactPerson> criteriaQuery = criteriaBuilder.createQuery(ContactPerson.class);
        Root<ContactPerson> root = criteriaQuery.from(ContactPerson.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(ContactPerson_.name)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
