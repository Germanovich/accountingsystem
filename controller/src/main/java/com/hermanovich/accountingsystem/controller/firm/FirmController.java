package com.hermanovich.accountingsystem.controller.firm;


import com.hermanovich.accountingsystem.controller.util.CarConverter;
import com.hermanovich.accountingsystem.controller.util.ContactPersonConverter;
import com.hermanovich.accountingsystem.controller.util.FirmConverter;
import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.firm.FirmDto;
import com.hermanovich.accountingsystem.service.firm.FirmService;
import com.hermanovich.accountingsystem.service.sortertype.FirmSorterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/firms")
public class FirmController {

    private final FirmService firmService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addFirm(@RequestBody FirmDto firmDto) {
        firmService.addFirm(FirmConverter.convertFirmDtoToFirm(firmDto));
        log.info("Firm added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeFirm(@PathVariable Integer id) {
        firmService.removeFirm(id);
        log.info("Firm deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateFirm(@RequestBody FirmDto firmDto) {
        firmService.updateFirm(FirmConverter.convertFirmDtoToFirm(firmDto));
        log.info("Firm updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public FirmDto getFirm(@PathVariable Integer id) {
        return FirmConverter.convertFirmToFirmDto(firmService.getFirm(id));
    }

    @GetMapping
    public List<FirmDto> getFirmList(@RequestParam(name = "type", required = false) FirmSorterType firmSorterType) {
        if (firmSorterType == null) {
            return FirmConverter.convertFirmListToFirmDtoList(firmService.getFirmList());
        }
        return FirmConverter.convertFirmListToFirmDtoList(firmService.getSortedFirmList(firmSorterType));
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<?> getContactPersonListByFirmId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ContactPersonConverter.convertContactPersonListToContactPersonDtoList(
                firmService.getContactPersonsListByFirmId(id)));
    }

    @GetMapping(value = "/{id}/cars")
    public List<CarDto> getCarListByFirmId(@PathVariable Integer id) {
        return CarConverter.convertCarListToCarDtoList(firmService.getCarListByFirmId(id));
    }
}
