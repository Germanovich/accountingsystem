package com.hermanovich.accountingsystem.dao.profile;

import com.hermanovich.accountingsystem.dao.HibernateAbstractDao;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.profile.Profile_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaUpdate;
import java.util.Date;

@Component
public class ProfileDaoImpl extends HibernateAbstractDao<Profile, Integer> implements ProfileDao {

    @Override
    protected void updateSet(CriteriaUpdate<Profile> criteriaUpdate, Profile profile) {
        criteriaUpdate.set(Profile_.name, profile.getName());
        criteriaUpdate.set(Profile_.surname, profile.getSurname());
        if (profile.getSex() == null) {
            criteriaUpdate.set(Profile_.sex, (Boolean) null);
        } else {
            criteriaUpdate.set(Profile_.sex, profile.getSex());
        }
        if (profile.getDateOfBirth() == null) {
            criteriaUpdate.set(Profile_.dateOfBirth, (Date) null);
        } else {
            criteriaUpdate.set(Profile_.dateOfBirth, profile.getDateOfBirth());
        }
        criteriaUpdate.set(Profile_.dateOfRegistration, profile.getDateOfRegistration());
    }

    @Override
    protected Class<Profile> getType() {
        return Profile.class;
    }
}
