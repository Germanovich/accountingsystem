package com.hermanovich.accountingsystem.service.contactperson;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.contactperson.ContactPersonDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.service.sortertype.ContactPersonSorterType;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactPersonServiceImpl implements ContactPersonService {

    private final ContactPersonDao contactPersonDao;
    private final FirmDao firmDao;

    @Transactional
    @Override
    public void addContactPerson(ContactPerson contactPerson) {
        verifyContactPersonData(contactPerson);
        contactPerson.setFirm(firmDao.getById(contactPerson.getFirm().getId()));
        contactPersonDao.save(contactPerson);
    }

    @Transactional
    @Override
    public void removeContactPerson(Integer id) {
        contactPersonDao.delete(id);
    }

    @Transactional
    @Override
    public void updateContactPerson(ContactPerson contactPerson) {
        if (contactPerson.getId() == null || contactPersonDao.getById(contactPerson.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CONTACT_PERSON_EXISTS.get());
        }
        verifyContactPersonData(contactPerson);
        contactPerson.setFirm(firmDao.getById(contactPerson.getFirm().getId()));
        contactPersonDao.update(contactPerson);
    }

    @Transactional
    @Override
    public ContactPerson getContactPerson(Integer id) {
        ContactPerson contactPerson = contactPersonDao.getById(id);
        if (contactPerson == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_CONTACT_PERSON_EXISTS.get());
        }
        return contactPerson;
    }

    @Transactional
    @Override
    public List<ContactPerson> getContactPersonsList() {
        return contactPersonDao.getAll();
    }

    @Transactional
    @Override
    public List<ContactPerson> getSortedContactPersonsList(ContactPersonSorterType sorterType) {
        if (ContactPersonSorterType.SORT_CONTACT_PERSON_BY_NAME.equals(sorterType)) {
            return contactPersonDao.getListByOrderByName();
        }
        return contactPersonDao.getAll();
    }

    private void verifyContactPersonData(ContactPerson contactPerson) {
        if (contactPerson.getFirm() == null ||
                contactPerson.getFirm().getId() == null ||
                contactPerson.getName() == null ||
                contactPerson.getTelephone() == null ||
                contactPerson.getName().isEmpty() ||
                contactPerson.getTelephone().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_CONTACT_PERSON_DATA.get());
        }
        if (firmDao.getById(contactPerson.getFirm().getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
    }
}
