package com.hermanovich.accountingsystem.dao.car;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.car.Car_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CarDaoImpl extends HibernateAbstractDao<Car, Integer> implements CarDao {

    @Override
    protected void updateSet(CriteriaUpdate<Car> criteriaUpdate, Car car) {
        criteriaUpdate.set(Car_.CATALOG, car.getCatalog().getId());
        criteriaUpdate.set(Car_.FIRM, car.getFirm().getId());
        criteriaUpdate.set(Car_.model, car.getModel());
        criteriaUpdate.set(Car_.carStatus, car.getCarStatus());
        criteriaUpdate.set(Car_.price, car.getPrice());
        if (car.getDescription() == null) {
            criteriaUpdate.set(Car_.description, (String) null);
        } else {
            criteriaUpdate.set(Car_.description, car.getDescription());
        }
    }

    @Override
    protected Class<Car> getType() {
        return Car.class;
    }

    @Override
    public List<Car> getListByCatalogId(Integer catalogId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Car_.catalog), catalogId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public List<Car> getListByFirmId(Integer firmId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.firm), firmId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByStatus(CarStatus carStatus) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.carStatus), carStatus));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByOrderByModel() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Car_.model)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByOrderByStatus() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Car_.carStatus)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByOrderByPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(Car_.price)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByStatusOrderByModel(CarStatus status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.carStatus), status))
                .orderBy(criteriaBuilder.desc(root.get(Car_.model)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> getListByStatusOrderByPrice(CarStatus status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.carStatus), status))
                .orderBy(criteriaBuilder.desc(root.get(Car_.price)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
