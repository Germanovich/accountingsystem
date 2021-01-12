package com.hermanovich.accountingsystem.controller.contactperson;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.controller.requests.RequestController;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.contactperson.ContactPersonService;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(RequestController.class)
@ContextConfiguration(classes = {Application.class})
class ContactPersonControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ContactPersonService contactPersonService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactPersonController_addContactPerson() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Mia")
                .telephone("375294567587")
                .firm(firm)
                .build();

        Mockito.doNothing().when(contactPersonService).addContactPerson(contactPerson);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/contacts")
                .content("{\"firm\": {" +
                        "        \"id\": 1" +
                        "    }," +
                        "    \"telephone\": \"375294567587\"," +
                        "    \"name\": \"Mia\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactPersonController_removeContactPerson() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(contactPersonService).removeContactPerson(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/contacts/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void ContactPersonController_updateContactPerson() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Mia")
                .telephone("375294567587")
                .firm(firm)
                .build();

        Mockito.doNothing().when(contactPersonService).updateContactPerson(contactPerson);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/contacts")
                .content("{\"firm\": {" +
                        "        \"id\": 1" +
                        "    }," +
                        "    \"telephone\": \"375294567587\"," +
                        "    \"name\": \"Mia\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactPersonController_getContactPerson() throws Exception {
        Integer id = 1;
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(id)
                .name("Mia")
                .telephone("375294567587")
                .firm(firm)
                .build();

        Mockito.doReturn(contactPerson).when(contactPersonService).getContactPerson(id);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/contacts/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void ContactPersonController_getContactPersonsList() throws Exception {
        Firm firm = Firm.builder()
                .id(1)
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

        Mockito.doReturn(contactPersonList).when(contactPersonService).getContactPersonsList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/contacts")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}