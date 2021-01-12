package com.hermanovich.accountingsystem.controller.firm;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.controller.requests.RequestController;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.firm.FirmService;
import com.hermanovich.accountingsystem.service.request.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(FirmController.class)
@ContextConfiguration(classes = {Application.class})
class FirmControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    FirmService firmService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void FirmController_addFirm() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doNothing().when(firmService).addFirm(firm);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/firms")
                .content("{\"name\": \"BMW\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void FirmController_removeFirm() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(firmService).removeFirm(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/firms/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void FirmController_updateFirm() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();

        Mockito.doNothing().when(firmService).updateFirm(firm);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/firms")
                .content("{\"name\": \"BMW\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void FirmController_getFirm() throws Exception {
        Integer firmId = 1;
        Firm firm = Firm.builder()
                .id(firmId)
                .name("BMW")
                .build();

        Mockito.doReturn(firm).when(firmService).getFirm(firmId);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/firms/{id}", firmId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void FirmController_getFirmList() throws Exception {
        List<Firm> firmList = new ArrayList<>();
        firmList.add(Firm.builder()
                .id(1)
                .name("BMW")
                .build());
        firmList.add(Firm.builder()
                .id(2)
                .name("Audi")
                .build());

        Mockito.doReturn(firmList).when(firmService).getFirmList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/firms")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void FirmController_getContactPersonsListByFirm() throws Exception {
        Integer firmId = 1;
        Firm firm = Firm.builder()
                .id(firmId)
                .build();
        List<ContactPerson> contactPersonList = new ArrayList<>();
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Mia")
                .telephone("375294567587")
                .firm(firm)
                .build());
        contactPersonList.add(ContactPerson.builder()
                .id(2)
                .name("Mike")
                .telephone("375294447587")
                .firm(firm)
                .build());

        Mockito.doReturn(contactPersonList).when(firmService).getContactPersonsListByFirmId(firmId);

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/firms/{id}/contacts", firmId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void FirmController_getCarListByFirmId() throws Exception {
        Integer id = 1;
        Firm firm = Firm.builder()
                .id(id)
                .build();
        Catalog catalog = Catalog.builder()
                .id(1)
                .build();
        List<Car> carList = new ArrayList<>();
        carList.add(Car.builder()
                .id(1)
                .firm(firm)
                .catalog(catalog)
                .carStatus(CarStatus.AVAILABLE)
                .model("X8")
                .price(new BigDecimal("198"))
                .build());
        carList.add(Car.builder()
                .id(2)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("144"))
                .build());
        carList.add(Car.builder()
                .id(3)
                .firm(firm)
                .catalog(catalog)
                .carStatus(CarStatus.AVAILABLE)
                .model("X9")
                .price(new BigDecimal("123"))
                .build());

        Mockito.doReturn(carList).when(firmService).getCarListByFirmId(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/firms/{id}/cars", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}