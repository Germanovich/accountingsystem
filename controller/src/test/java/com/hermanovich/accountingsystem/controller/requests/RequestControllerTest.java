package com.hermanovich.accountingsystem.controller.requests;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
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

@WebMvcTest(RequestController.class)
@ContextConfiguration(classes = {Application.class})
class RequestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RequestService requestService;

    @Test
    @WithMockUser(roles = "USER")
    void RequestController_addRequest() throws Exception {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Request request = Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doNothing().when(requestService).addRequest(request);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/requests")
                .content("{\"car\": {" +
                        "        \"id\": 1" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void RequestController_removeRequest() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(requestService).removeRequest(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/requests/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void RequestController_updateRequest() throws Exception {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.MISSING)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Request request = Request.builder()
                .car(car)
                .user(user)
                .build();

        Mockito.doNothing().when(requestService).addRequest(request);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/requests")
                .content("{\"id\":6," +
                        "    \"user\": {" +
                        "        \"id\": 2" +
                        "    }," +
                        "    \"car\": {" +
                        "        \"id\": 1" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void RequestController_getRequest() throws Exception {
        Integer id = 10;
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .role(UserRole.USER)
                .profile(Profile.builder().build())
                .id(1)
                .build();
        Request request = Request.builder()
                .id(id)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(request).when(requestService).getRequest(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/requests/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void RequestController_getRequestList() throws Exception {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .role(UserRole.USER)
                .profile(Profile.builder().build())
                .id(1)
                .build();
        List<Request> requestList = new ArrayList<>();
        requestList.add(Request.builder()
                .id(1)
                .car(car)
                .user(user)
                .build());
        requestList.add(Request.builder()
                .id(2)
                .car(car)
                .user(user)
                .build());

        Mockito.doReturn(requestList).when(requestService).getRequestList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/requests")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}