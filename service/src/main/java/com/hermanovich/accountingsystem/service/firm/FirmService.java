package com.hermanovich.accountingsystem.service.firm;

import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import com.hermanovich.accountingsystem.model.firm.Firm;
import com.hermanovich.accountingsystem.service.sortertype.FirmSorterType;

import java.util.List;

public interface FirmService {

    void addFirm(Firm firm);

    void removeFirm(Integer id);

    void updateFirm(Firm firm);

    Firm getFirm(Integer id);

    List<Firm> getFirmList();

    List<Firm> getSortedFirmList(FirmSorterType sorterType);

    List<ContactPerson> getContactPersonsListByFirmId(Integer firmId);

    List<Car> getCarListByFirmId(Integer firmId);
}
