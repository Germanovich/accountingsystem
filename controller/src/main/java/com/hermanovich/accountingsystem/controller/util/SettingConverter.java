package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.setting.SettingDto;
import com.hermanovich.accountingsystem.model.setting.Setting;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class SettingConverter {

    public Setting convertSettingDtoToRole(SettingDto settingDto) {
        return Setting.builder()
                .id(settingDto.getId())
                .name(settingDto.getName())
                .access(settingDto.getAccess())
                .build();
    }
}
