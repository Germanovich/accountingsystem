package com.hermanovich.accountingsystem.dao.contactperson;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;

import java.util.List;

public interface ContactPersonDao extends GenericDao<ContactPerson, Integer> {

    List<ContactPerson> getListByFirmId(Integer firmId);

    List<ContactPerson> getListByOrderByName();
}
