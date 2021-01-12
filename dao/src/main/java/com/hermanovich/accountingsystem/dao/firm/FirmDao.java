package com.hermanovich.accountingsystem.dao.firm;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.firm.Firm;

import java.util.List;

public interface FirmDao extends GenericDao<Firm, Integer> {

    List<Firm> getListByOrderByName();
}
