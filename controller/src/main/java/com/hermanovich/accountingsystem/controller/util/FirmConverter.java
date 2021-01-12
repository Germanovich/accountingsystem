package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.firm.FirmDto;
import com.hermanovich.accountingsystem.model.firm.Firm;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class FirmConverter {

    public FirmDto convertFirmToFirmDto(Firm firm) {
        return FirmDto.builder()
                .id(firm.getId())
                .name(firm.getName())
                .build();
    }

    public Firm convertFirmDtoToFirm(FirmDto firmDto) {
        return Firm.builder()
                .id(firmDto.getId())
                .name(firmDto.getName())
                .build();
    }

    public List<FirmDto> convertFirmListToFirmDtoList(List<Firm> firmList) {
        List<FirmDto> firmDtoList = new ArrayList<>();
        for (Firm firm : firmList) {
            firmDtoList.add(convertFirmToFirmDto(firm));
        }
        return firmDtoList;
    }
}
