package com.hermanovich.accountingsystem.controller.order;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import com.hermanovich.accountingsystem.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {Application.class})
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;

    @Test
    @WithMockUser(roles = "USER")
    void OrderController_addOrder() throws Exception {
        Car car = Car.builder()
                .id(4)
                .build();
        User user = User.builder()
                .id(1)
                .login("user")
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doNothing().when(orderService).addOrder(order, 12);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/orders?days=12")
                .content("{\"car\": {" +
                        "        \"id\": 4" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void OrderController_deleteOrder() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(orderService).removeOrder(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/orders/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void OrderController_closeOrder() throws Exception {
        Mockito.doNothing().when(orderService).closeOrder(ArgumentMatchers.anyString());

        mockMvc.perform(RestDocumentationRequestBuilders.put("/orders/completion")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void OrderController_updateOrder() throws Exception {
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .build();
        User user = User.builder()
                .id(1)
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build();

        Mockito.doNothing().when(orderService).updateOrder(order);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/orders")
                .content("{\"id\": 1," +
                        "\"user\": {" +
                        "        \"id\": 1" +
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
    void OrderController_getOrder() throws Exception {
        Integer id = 1;
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
        Order order = Order.builder()
                .id(id)
                .car(car)
                .user(user)
                .build();

        Mockito.doReturn(order).when(orderService).getOrder(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/orders/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void OrderController_getOrderList() throws Exception {
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
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build());
        orderList.add(Order.builder()
                .id(2)
                .car(car)
                .user(user)
                .build());

        Mockito.doReturn(orderList).when(orderService).getOrderList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/orders")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void OrderController_getUserOrders() throws Exception {
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
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .build());
        orderList.add(Order.builder()
                .id(2)
                .car(car)
                .user(user)
                .build());

        Mockito.doReturn(orderList).when(orderService).getOrderList(user.getLogin());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/orders/histories")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void OrderController_getAmountMoney() throws Exception {
        Integer days = 10;
        Mockito.doReturn(new BigDecimal("1478")).when(orderService).getAmountMoney(days);

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/orders/amountMoney?days=10")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}