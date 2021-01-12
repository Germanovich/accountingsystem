package com.hermanovich.accountingsystem.service.setting;

import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.setting.Setting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingServiceImplTest {

    @InjectMocks
    SettingServiceImpl settingService;
    @Mock
    SettingDao settingDao;

    @Test
    void SettingServiceImpl_updateSetting() {
        Setting setting = Setting.builder()
                .id(1)
                .name("configurationNameForCreationUser")
                .access(Boolean.TRUE)
                .build();

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doNothing().when(settingDao).update(setting);

        settingService.updateSetting(setting);

        Mockito.verify(settingDao).update(setting);
    }

    @Test
    void SettingServiceImpl_updateSetting_whenSettingNotFoundToThrowBusinessException() {
        Setting setting = Setting.builder()
                .name("configurationNameForCreationUser")
                .build();

        Mockito.doReturn(null).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> settingService.updateSetting(setting));
    }

    @Test
    void SettingServiceImpl_updateSetting_whenSettingHasEmptyNameToThrowBusinessException() {
        Setting setting = Setting.builder()
                .name("")
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> settingService.updateSetting(setting));
    }
}