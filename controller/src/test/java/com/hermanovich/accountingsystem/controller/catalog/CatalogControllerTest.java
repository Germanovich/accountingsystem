package com.hermanovich.accountingsystem.controller.catalog;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.controller.requests.RequestController;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.service.catalog.CatalogService;
import com.hermanovich.accountingsystem.service.request.RequestService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CatalogController.class)
@ContextConfiguration(classes = {Application.class})
class CatalogControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CatalogService catalogService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void CatalogController_addCatalog() throws Exception {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .parentCatalog(Catalog.builder().id(2).build())
                .build();

        Mockito.doNothing().when(catalogService).addCatalog(catalog);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/catalogs")
                .content("{\"name\": \"Amphibious vehicle\"," +
                        "    \"parentCatalog\": {" +
                        "        \"id\": 1" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CatalogController_removeCatalog() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(catalogService).removeCatalog(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/catalogs/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CatalogController_updateCatalog() throws Exception {
        Catalog catalog = Catalog.builder()
                .id(7)
                .name("Amphibious")
                .parentCatalog(Catalog.builder().id(4).build())
                .build();

        Mockito.doNothing().when(catalogService).addCatalog(catalog);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/catalogs")
                .content("{\"id\": 7," +
                        "    \"name\": \"Amphibious\"," +
                        "    \"parentCatalog\": {" +
                        "        \"id\": 4" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CatalogController_getCatalog() throws Exception {
        Integer id = 7;
        Catalog catalog = Catalog.builder()
                .id(id)
                .name("Amphibious")
                .parentCatalog(Catalog.builder().id(4).build())
                .build();

        Mockito.doReturn(catalog).when(catalogService).getCatalog(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/catalogs/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CatalogController_getListByParentCatalogId() throws Exception {
        Integer parentCatalogId = 7;
        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Amphibious")
                .parentCatalog(Catalog.builder().id(parentCatalogId).build())
                .build());
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Long car")
                .parentCatalog(Catalog.builder().id(parentCatalogId).build())
                .build());

        Mockito.doReturn(catalogList).when(catalogService).getListByParentCatalogId(parentCatalogId);

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/catalogs/{id}/childCatalogs", parentCatalogId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CatalogController_getList() throws Exception {

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Amphibious")
                .parentCatalog(Catalog.builder().id(1).build())
                .build());
        catalogList.add(Catalog.builder()
                .id(1)
                .name("Long car")
                .parentCatalog(Catalog.builder().id(2).build())
                .build());

        Mockito.doReturn(catalogList).when(catalogService).getList();

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/catalogs")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CatalogController_getCarListByCatalogId() throws Exception {
        Integer id = 1;
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Amphibious")
                .parentCatalog(Catalog.builder().id(2).build())
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

        Mockito.doReturn(carList).when(catalogService).getCarListByCatalogId(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/catalogs/{id}/cars", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}