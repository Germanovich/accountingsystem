package com.hermanovich.accountingsystem.service.contactperson;

import com.hermanovich.accountingsystem.dao.contactperson.ContactPersonDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.sortertype.ContactPersonSorterType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ContactPersonServiceImplTest {

    @InjectMocks
    ContactPersonServiceImpl contactPersonService;
    @Mock
    ContactPersonDao contactPersonDao;
    @Mock
    FirmDao firmDao;

    @Test
    void ContactPersonServiceImpl_addContactPerson() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .telephone("+375291584744")
                .build();

        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(contactPersonDao).save(ArgumentMatchers.any());

        contactPersonService.addContactPerson(contactPerson);

        Mockito.verify(contactPersonDao).save(contactPerson);
    }

    @Test
    void ContactPersonServiceImpl_addContactPerson_whenContactPersonWithoutFirmToThrowBusinessException() {
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> contactPersonService.addContactPerson(contactPerson));
    }

    @Test
    void ContactPersonServiceImpl_addContactPerson_whenNoSuchFirmToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .telephone("+375291584744")
                .firm(firm)
                .build();

        Mockito.doReturn(null).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> contactPersonService.addContactPerson(contactPerson));
    }

    @Test
    void ContactPersonServiceImpl_removeContactPerson() {
        Integer contactPersonId = 1;

        Mockito.doNothing().when(contactPersonDao).delete(ArgumentMatchers.anyInt());

        contactPersonService.removeContactPerson(contactPersonId);
        Mockito.verify(contactPersonDao).delete(ArgumentMatchers.anyInt());
    }

    @Test
    void ContactPersonServiceImpl_updateContactPerson() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .telephone("+375291584744")
                .firm(firm)
                .build();

        Mockito.doReturn(contactPerson).when(contactPersonDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(firm).when(firmDao).getById(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(contactPersonDao).update(ArgumentMatchers.any());

        contactPersonService.updateContactPerson(contactPerson);

        Mockito.verify(contactPersonDao).update(contactPerson);
    }

    @Test
    void ContactPersonServiceImpl_updateContactPerson_whenContactPersonWithoutFirmToThrowBusinessException() {
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .build();

        Mockito.doReturn(contactPerson).when(contactPersonDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> contactPersonService.updateContactPerson(contactPerson));
    }

    @Test
    void ContactPersonServiceImpl_updateContactPerson_whenNoSuchFirmToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .telephone("+375291584744")
                .firm(firm)
                .build();

        Mockito.doReturn(contactPerson).when(contactPersonDao).getById(ArgumentMatchers.anyInt());
        Mockito.doReturn(null).when(firmDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> contactPersonService.updateContactPerson(contactPerson));
    }

    @Test
    void ContactPersonServiceImpl_updateContactPerson_whenNoSuchContactPersonToThrowBusinessException() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .build();

        Mockito.doReturn(null).when(contactPersonDao).getById(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> contactPersonService.updateContactPerson(contactPerson));
    }

    @Test
    void ContactPersonServiceImpl_getContactPerson() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        ContactPerson contactPerson = ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .build();

        Mockito.doReturn(contactPerson).when(contactPersonDao).getById(ArgumentMatchers.anyInt());

        contactPersonService.getContactPerson(contactPerson.getId());

        Mockito.verify(contactPersonDao).getById(contactPerson.getId());
    }

    @Test
    void ContactPersonServiceImpl_getContactPersonList() {
        Firm firm = Firm.builder()
                .id(1)
                .build();
        List<ContactPerson> contactPersonList = new ArrayList<>();
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .build());
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Tima")
                .firm(firm)
                .build());

        Mockito.doReturn(contactPersonList).when(contactPersonDao).getAll();

        List<ContactPerson> personList = contactPersonService.getContactPersonsList();

        Assertions.assertNotNull(personList);
        for (ContactPerson contactPerson : personList) {
            Assertions.assertNotNull(contactPerson);
        }
        Mockito.verify(contactPersonDao).getAll();
    }

    @Test
    void ContactPersonServiceImpl_getSortedContactPersonList() {
        ContactPersonSorterType sorterType = ContactPersonSorterType.SORT_CONTACT_PERSON_BY_NAME;
        Firm firm = Firm.builder()
                .id(1)
                .build();
        List<ContactPerson> contactPersonList = new ArrayList<>();
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Dima")
                .firm(firm)
                .build());
        contactPersonList.add(ContactPerson.builder()
                .id(1)
                .name("Tima")
                .firm(firm)
                .build());

        Mockito.doReturn(contactPersonList).when(contactPersonDao).getListByOrderByName();

        List<ContactPerson> personList = contactPersonService.getSortedContactPersonsList(sorterType);

        Assertions.assertNotNull(personList);
        for (ContactPerson contactPerson : personList) {
            Assertions.assertNotNull(contactPerson);
        }
        Mockito.verify(contactPersonDao).getListByOrderByName();
    }
}