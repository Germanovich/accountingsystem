package com.hermanovich.accountingsystem.dao.user;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.User_;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Component
public class UserDaoImpl extends HibernateAbstractDao<User, Integer> implements UserDao {

    @Override
    protected void updateSet(CriteriaUpdate<User> criteriaUpdate, User user) {
        criteriaUpdate.set(User_.login, user.getLogin());
        criteriaUpdate.set(User_.password, user.getPassword());
        criteriaUpdate.set(User_.role, user.getRole());
        criteriaUpdate.set(User_.PROFILE, user.getProfile().getId());
    }

    @Override
    protected Class<User> getType() {
        return User.class;
    }

    @Override
    public User getByLogin(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> model = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(model.get(User_.login), login));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
