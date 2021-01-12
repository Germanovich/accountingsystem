package com.hermanovich.accountingsystem.service.firm;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.car.CarDao;
import com.hermanovich.accountingsystem.dao.contactperson.ContactPersonDao;
import com.hermanovich.accountingsystem.dao.firm.FirmDao;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.sortertype.FirmSorterType;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FirmServiceImpl implements FirmService {

    private final FirmDao firmDao;
    private final CarDao carDao;
    private final ContactPersonDao contactPersonDao;

    @Transactional
    @Override
    public void addFirm(Firm firm) {
        verifyFirmData(firm);
        firmDao.save(firm);
    }

    @Transactional
    @Override
    public void removeFirm(Integer id) {
        if (carDao.getListByFirmId(id).isEmpty() && contactPersonDao.getListByFirmId(id).isEmpty()) {
            firmDao.delete(id);
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_FIRM_HAS_CARS_OR_CONTACT_PERSONS.get());
    }

    @Transactional
    @Override
    public void updateFirm(Firm firm) {
        if (firm.getId() == null || firmDao.getById(firm.getId()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
        verifyFirmData(firm);
        firmDao.update(firm);
    }

    @Transactional
    @Override
    public Firm getFirm(Integer id) {
        Firm firm = firmDao.getById(id);
        if (firm == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
        return firm;
    }

    @Transactional
    @Override
    public List<Firm> getFirmList() {
        return firmDao.getAll();
    }

    @Transactional
    @Override
    public List<Firm> getSortedFirmList(FirmSorterType sorterType) {
        if (FirmSorterType.SORT_FIRM_BY_NAME.equals(sorterType)) {
            return firmDao.getListByOrderByName();
        }
        return firmDao.getAll();
    }

    @Transactional
    @Override
    public List<ContactPerson> getContactPersonsListByFirmId(Integer firmId) {
        if (firmDao.getById(firmId) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
        return contactPersonDao.getListByFirmId(firmId);
    }

    @Transactional
    @Override
    public List<Car> getCarListByFirmId(Integer firmId) {
        if (firmDao.getById(firmId) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_FIRM_EXISTS.get());
        }
        return carDao.getListByFirmId(firmId);
    }

    private void verifyFirmData(Firm firm) {
        if (firm.getName() == null || firm.getName().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_FIRM_NAME_IS_MISSING.get());
        }
    }
}
