package com.hermanovich.accountingsystem.service.setting;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SettingServiceImpl implements SettingService {

    private final SettingDao settingDao;

    @Transactional
    @Override
    public void updateSetting(Setting setting) {
        verifySettingData(setting);
        setting.setId(settingDao.getSettingByName(setting.getName()).getId());
        settingDao.update(setting);
    }

    private void verifySettingData(Setting setting) {
        if (setting.getName() == null || setting.getName().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_SETTING_DATA.get());
        }
        if (settingDao.getSettingByName(setting.getName()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_SETTING_EXISTS.get());
        }
    }
}
