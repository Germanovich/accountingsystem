package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.contactperson.ContactPersonDto;
import com.hermanovich.accountingsystem.model.contactperson.ContactPerson;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class ContactPersonConverter {

    public ContactPersonDto convertContactPersonToContactPersonDto(ContactPerson contactPerson) {
        return ContactPersonDto.builder()
                .id(contactPerson.getId())
                .firm(FirmConverter.convertFirmToFirmDto(contactPerson.getFirm()))
                .telephone(contactPerson.getTelephone())
                .name(contactPerson.getName())
                .build();
    }

    public ContactPerson convertContactPersonDtoToContactPerson(ContactPersonDto contactPersonDto) {
        return ContactPerson.builder()
                .id(contactPersonDto.getId())
                .firm(contactPersonDto.getFirm() == null ?
                        null : FirmConverter.convertFirmDtoToFirm(contactPersonDto.getFirm()))
                .telephone(contactPersonDto.getTelephone())
                .name(contactPersonDto.getName())
                .build();
    }

    public List<ContactPersonDto> convertContactPersonListToContactPersonDtoList(List<ContactPerson> contactPersonList) {
        List<ContactPersonDto> contactPersonDtoList = new ArrayList<>();
        for (ContactPerson contactPerson : contactPersonList) {
            contactPersonDtoList.add(convertContactPersonToContactPersonDto(contactPerson));
        }
        return contactPersonDtoList;
    }
}
