package com.hermanovich.accountingsystem.controller.setting;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.controller.requests.RequestController;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.service.request.RequestService;
import com.hermanovich.accountingsystem.service.setting.SettingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SettingController.class)
@ContextConfiguration(classes = {Application.class})
class SettingControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SettingService settingService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void SettingController_updateSetting() throws Exception {

        Setting setting = Setting.builder()
                .id(1)
                .name("requestFunctionEnabled")
                .access(Boolean.FALSE)
                .build();

        Mockito.doNothing().when(settingService).updateSetting(setting);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/settings")
                .content("{\"name\": \"requestFunctionEnabled\"," +
                        "    \"access\": false}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}