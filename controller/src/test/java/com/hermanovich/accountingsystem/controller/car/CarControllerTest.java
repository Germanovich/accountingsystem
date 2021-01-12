package com.hermanovich.accountingsystem.controller.car;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.car.CarStatus;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import com.hermanovich.accountingsystem.service.car.CarService;
import com.hermanovich.accountingsystem.util.DataManagerSystem;
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

@WebMvcTest(CarController.class)
@ContextConfiguration(classes = {Application.class})
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CarService carService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_addCar() throws Exception {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doNothing().when(carService).addCar(car);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/cars")
                .content("{\"catalog\": {" +
                        "        \"id\": 2" +
                        "    }," +
                        "    \"firm\": {" +
                        "        \"id\": 5" +
                        "    }," +
                        "    \"model\": \"DB11 Coupe\"," +
                        "    \"carStatus\": \"AVAILABLE\"," +
                        "    \"price\": 140.00}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_removeCar() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(carService).removeCar(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/cars/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_updateCar() throws Exception {
        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doNothing().when(carService).updateCar(car);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/cars")
                .content("{\"catalog\": {" +
                        "        \"id\": 2" +
                        "    }," +
                        "    \"firm\": {" +
                        "        \"id\": 5" +
                        "    }," +
                        "    \"model\": \"DB11 Coupe\"," +
                        "    \"carStatus\": \"AVAILABLE\"," +
                        "    \"price\": 140.00}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_changeCarStatus() throws Exception {
        Integer id = 10;
        CarStatus status = CarStatus.MISSING;

        Mockito.doNothing().when(carService).changeCarStatus(id, status);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/cars/{id}?status=MISSING", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CarController_getCar() throws Exception {
        Integer id = 1;

        Catalog catalog = Catalog.builder()
                .id(1)
                .name("Car")
                .build();
        Firm firm = Firm.builder()
                .id(1)
                .name("BMW")
                .build();
        Car car = Car.builder()
                .id(1)
                .catalog(catalog)
                .firm(firm)
                .carStatus(CarStatus.AVAILABLE)
                .model("X7")
                .price(new BigDecimal("177"))
                .build();

        Mockito.doReturn(car).when(carService).getCar(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/cars/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void CarController_getCarList() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
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

        Mockito.doReturn(carList).when(carService).getCarList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/cars")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_getCountOrders() throws Exception {
        Integer carId = 5;

        Mockito.doReturn(10).when(carService).getCountOrdersByCarId(carId);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/cars/{id}/countOrders", carId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_getCountCarsByStatus() throws Exception {
        Integer carId = 5;
        CarStatus carStatus = CarStatus.AVAILABLE;

        Mockito.doReturn(10).when(carService).getCountCarsByStatus(carStatus);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/cars/countCars?status=AVAILABLE", carId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void CarController_getOrdersByCarId() throws Exception {
        Integer carId = 5;
        Car car = Car.builder()
                .id(1)
                .catalog(Catalog.builder().id(1).build())
                .firm(Firm.builder().id(1).build())
                .carStatus(CarStatus.AVAILABLE)
                .price(new BigDecimal("100"))
                .model("BMW")
                .build();
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("test111")
                .profile(profile)
                .build();
        Order order = Order.builder()
                .id(1)
                .car(car)
                .user(user)
                .startDate(DataManagerSystem.getDateDaysAgo(5))
                .endDate(DataManagerSystem.getDateDaysAgo(4))
                .actualEndDate(DataManagerSystem.getDateDaysAgo(1))
                .price(new BigDecimal("400"))
                .build();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order);
        orderList.add(order);

        Mockito.doReturn(orderList).when(carService).getOrdersByCarId(carId);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/cars/{id}/orders", carId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}