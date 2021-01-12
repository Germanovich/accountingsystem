package com.hermanovich.accountingsystem.dao.order;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.order.Order_;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class OrderDaoImpl extends HibernateAbstractDao<Order, Integer> implements OrderDao {

    @Override
    protected void updateSet(CriteriaUpdate<Order> criteriaUpdate, Order order) {
        criteriaUpdate.set(Order_.USER, order.getUser().getId());
        criteriaUpdate.set(Order_.CAR, order.getCar().getId());
        criteriaUpdate.set(Order_.startDate, new Timestamp(order.getStartDate().getTime()));
        criteriaUpdate.set(Order_.endDate, new Timestamp(order.getEndDate().getTime()));
        criteriaUpdate.set(Order_.price, order.getPrice());
        if (order.getActualEndDate() == null) {
            criteriaUpdate.set(Order_.actualEndDate, (Date) null);
        } else {
            criteriaUpdate.set(Order_.actualEndDate, new Timestamp(order.getActualEndDate().getTime()));
        }
    }

    @Override
    protected Class<Order> getType() {
        return Order.class;
    }

    @Override
    public Boolean isUserHaveOrder(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Order_.user), userId),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))));
        if (entityManager.createQuery(criteriaQuery).getResultList().isEmpty()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Order getValidOrderByUserId(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Order_.user), userId),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> getListByCarId(Integer carId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Order_.car), carId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getListByUserId(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Order_.user), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getNotExpiredOrderList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get(Order_.endDate),
                                        new Timestamp(System.currentTimeMillis())),
                                criteriaBuilder.isNull(root.get(Order_.actualEndDate))));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getExpiredOrderList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                                criteriaBuilder.lessThan(root.get(Order_.endDate),
                                        new Timestamp(System.currentTimeMillis())),
                                criteriaBuilder.isNull(root.get(Order_.actualEndDate))));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getListByOrderByStartDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Order_.startDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getListByOrderByEndDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Order_.endDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getListByOrderByPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Order_.price)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getListByOrderByStatus() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Order_.actualEndDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getNotExpiredOrderListByOrderByStartDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.startDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getNotExpiredOrderListByOrderByEndDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.endDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getNotExpiredOrderListByOrderByPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.price)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getNotExpiredOrderListByOrderByStatus() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.actualEndDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getExpiredOrderListByOrderByStartDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.lessThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.startDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getExpiredOrderListByOrderByEndDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.lessThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.endDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getExpiredOrderListByOrderByPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.lessThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.price)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Order> getExpiredOrderListByOrderByStatus() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.lessThan(root.get(Order_.endDate),
                                new Timestamp(System.currentTimeMillis())),
                        criteriaBuilder.isNull(root.get(Order_.actualEndDate))))
                .orderBy(criteriaBuilder.desc(root.get(Order_.actualEndDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
