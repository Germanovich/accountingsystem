package com.hermanovich.accountingsystem.dao.setting;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.setting.Setting;

public interface SettingDao extends GenericDao<Setting, Integer> {

    Setting getSettingByName(String name);
}
