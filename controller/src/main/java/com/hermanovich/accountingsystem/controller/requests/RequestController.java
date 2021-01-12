package com.hermanovich.accountingsystem.controller.requests;

import com.hermanovich.accountingsystem.controller.security.util.AuthenticationHelper;
import com.hermanovich.accountingsystem.controller.util.RequestConverter;
import com.hermanovich.accountingsystem.dto.request.RequestDto;
import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import com.hermanovich.accountingsystem.service.request.RequestService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<Void> addRequest(@RequestBody RequestDto requestDto) {
        requestDto.setUser(UserDetailDto.builder().login(AuthenticationHelper.getAuthenticationUserName()).build());
        requestService.addRequest(RequestConverter.convertRequestDtoToRequest(requestDto));
        log.info("Request added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeRequest(@PathVariable Integer id) {
        requestService.removeRequest(id);
        log.info("Request deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateRequest(@RequestBody RequestDto requestDto) {
        requestService.updateRequest(RequestConverter.convertRequestDtoToRequest(requestDto));
        log.info("Request updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RequestDto getRequest(@PathVariable Integer id) {
        return RequestConverter.convertRequestToRequestDto(requestService.getRequest(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RequestDto> getRequestList() {
        return RequestConverter.convertRequestListToRequestDtoList(requestService.getRequestList());
    }
}
