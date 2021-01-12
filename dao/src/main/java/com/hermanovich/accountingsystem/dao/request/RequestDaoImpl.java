package com.hermanovich.accountingsystem.dao.request;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.order.Order_;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.request.Request_;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Log4j2
@Component
public class RequestDaoImpl extends HibernateAbstractDao<Request, Integer> implements RequestDao {

    @Override
    protected void updateSet(CriteriaUpdate<Request> criteriaUpdate, Request request) {
        criteriaUpdate.set(Order_.USER, request.getUser().getId());
        criteriaUpdate.set(Order_.CAR, request.getCar().getId());
    }

    @Override
    protected Class<Request> getType() {
        return Request.class;
    }

    @Override
    public List<Request> getListByCarId(Integer carId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> criteriaQuery = criteriaBuilder.createQuery(Request.class);
        Root<Request> root = criteriaQuery.from(Request.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Request_.car), carId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Request> getListByUserId(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> criteriaQuery = criteriaBuilder.createQuery(Request.class);
        Root<Request> root = criteriaQuery.from(Request.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Request_.user), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Request getRequestByCarIdAndUserId(Integer carId, Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> criteriaQuery = criteriaBuilder.createQuery(Request.class);
        Root<Request> root = criteriaQuery.from(Request.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Request_.user), userId),
                        criteriaBuilder.equal(root.get(Request_.car), carId)));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteByCarId(Integer carId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Request> criteriaDelete = criteriaBuilder.createCriteriaDelete(getType());
        Root<Request> root = criteriaDelete.from(getType());
        criteriaDelete.where(criteriaBuilder.equal(root.get(Request_.car), carId));
        Integer number = entityManager.createQuery(criteriaDelete).executeUpdate();
        if (number.equals(0)) {
            log.info(MessageForUser.MESSAGE_NO_REQUESTS.get());
        }
    }
}
