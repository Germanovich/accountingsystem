package com.hermanovich.accountingsystem.controller.setting;

import com.hermanovich.accountingsystem.controller.util.SettingConverter;
import com.hermanovich.accountingsystem.dto.setting.SettingDto;
import com.hermanovich.accountingsystem.service.setting.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/settings")
public class SettingController {

    private final SettingService settingService;

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateSetting(@RequestBody SettingDto settingDto) {
        settingService.updateSetting(SettingConverter.convertSettingDtoToRole(settingDto));
        log.info("Setting updated");
        return ResponseEntity.ok().build();
    }
}
