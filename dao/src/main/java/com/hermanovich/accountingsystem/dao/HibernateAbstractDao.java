package com.hermanovich.accountingsystem.dao;

import com.hermanovich.accountingsystem.common.exception.DaoException;
import com.hermanovich.accountingsystem.model.AEntity;
import com.hermanovich.accountingsystem.model.AEntity_;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Log4j2
public abstract class HibernateAbstractDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void save(T entity) {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            log.error(MessageForUser.MESSAGE_SAVE_ERROR.get(), e);
            throw new DaoException(MessageForUser.MESSAGE_SAVE_ERROR.get());
        }
    }

    @Override
    public void update(T entity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getType());
        Root<T> root = criteriaUpdate.from(getType());
        updateSet(criteriaUpdate, entity);
        criteriaUpdate.where(criteriaBuilder.equal(root.get(AEntity_.id), entity.getId()));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void delete(PK id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(getType());
        Root<T> root = criteriaDelete.from(getType());
        criteriaDelete.where(criteriaBuilder.equal(root.get(AEntity_.id), id));
        Integer number = entityManager.createQuery(criteriaDelete).executeUpdate();
        if (number.equals(0)) {
            log.error(MessageForUser.MESSAGE_CANNOT_DELETE.get());
            throw new DaoException(MessageForUser.MESSAGE_CANNOT_DELETE.get());
        }
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getType());
        Root<T> root = criteriaQuery.from(getType());
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T getById(PK id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getType());
        Root<T> root = criteriaQuery.from(getType());
        criteriaQuery.where(criteriaBuilder.equal(root.get(AEntity_.id), id));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected abstract void updateSet(CriteriaUpdate<T> criteriaUpdate, T entity);

    protected abstract Class<T> getType();
}
