package com.hermanovich.accountingsystem.service.contactperson;

import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.service.sortertype.ContactPersonSorterType;

import java.util.List;

public interface ContactPersonService {

    void addContactPerson(ContactPerson contactPerson);

    void removeContactPerson(Integer id);

    void updateContactPerson(ContactPerson contactPerson);

    ContactPerson getContactPerson(Integer id);

    List<ContactPerson> getContactPersonsList();

    List<ContactPerson> getSortedContactPersonsList(ContactPersonSorterType sorterType);
}
