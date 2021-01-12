package com.hermanovich.accountingsystem.controller.contactperson;

import com.hermanovich.accountingsystem.controller.util.ContactPersonConverter;
import com.hermanovich.accountingsystem.dto.contactperson.ContactPersonDto;
import com.hermanovich.accountingsystem.service.contactperson.ContactPersonService;
import com.hermanovich.accountingsystem.service.sortertype.ContactPersonSorterType;
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
@RequestMapping("/contacts")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addContactPerson(@RequestBody ContactPersonDto contactPersonDto) {
        contactPersonService.addContactPerson(
                ContactPersonConverter.convertContactPersonDtoToContactPerson(contactPersonDto));
        log.info("Contact person added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeContactPerson(@PathVariable Integer id) {
        contactPersonService.removeContactPerson(id);
        log.info("Contact person deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateContactPerson(@RequestBody ContactPersonDto contactPersonDto) {
        contactPersonService.updateContactPerson(
                ContactPersonConverter.convertContactPersonDtoToContactPerson(contactPersonDto));
        log.info("Contact person updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ContactPersonDto getContactPerson(@PathVariable Integer id) {
        return ContactPersonConverter.convertContactPersonToContactPersonDto(
                        contactPersonService.getContactPerson(id));
    }

    @GetMapping
    public List<ContactPersonDto> getContactPersonList(
            @RequestParam(name = "type", required = false) ContactPersonSorterType contactPersonSorterType) {
        if (contactPersonSorterType == null) {
            return ContactPersonConverter.convertContactPersonListToContactPersonDtoList(
                            contactPersonService.getContactPersonsList());
        }
        return ContactPersonConverter.convertContactPersonListToContactPersonDtoList(
                        contactPersonService.getSortedContactPersonsList(contactPersonSorterType));
    }
}
