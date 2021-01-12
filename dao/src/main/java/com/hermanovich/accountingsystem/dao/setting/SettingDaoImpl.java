package com.hermanovich.accountingsystem.dao.setting;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.model.setting.Setting_;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Log4j2
@Component
public class SettingDaoImpl extends HibernateAbstractDao<Setting, Integer> implements SettingDao {

    @Override
    protected void updateSet(CriteriaUpdate<Setting> criteriaUpdate, Setting setting) {
        criteriaUpdate.set(Setting_.name, setting.getName());
        criteriaUpdate.set(Setting_.access, setting.getAccess());
    }

    @Override
    protected Class<Setting> getType() {
        return Setting.class;
    }

    @Override
    public Setting getSettingByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Setting> criteriaQuery = criteriaBuilder.createQuery(Setting.class);
        Root<Setting> model = criteriaQuery.from(Setting.class);
        criteriaQuery.where(criteriaBuilder.equal(model.get(Setting_.name), name));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            log.error(MessageForUser.MESSAGE_NO_RECORDS_FOUND.get());
            return null;
        }
    }
}
