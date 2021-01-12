package com.hermanovich.accountingsystem.controller.catalog;

import com.hermanovich.accountingsystem.controller.util.CarConverter;
import com.hermanovich.accountingsystem.controller.util.CatalogConverter;
import com.hermanovich.accountingsystem.dto.car.CarDto;
import com.hermanovich.accountingsystem.dto.catalog.CatalogDto;
import com.hermanovich.accountingsystem.service.catalog.CatalogService;
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
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addCatalog(@RequestBody CatalogDto catalogDto) {
        catalogService.addCatalog(CatalogConverter.convertCatalogDtoToCatalog(catalogDto));
        log.info("Catalog added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeCatalog(@PathVariable Integer id) {
        catalogService.removeCatalog(id);
        log.info("Catalog deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCatalog(@RequestBody CatalogDto catalogDto) {
        catalogService.updateCatalog(CatalogConverter.convertCatalogDtoToCatalog(catalogDto));
        log.info("Catalog updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public CatalogDto getCatalog(@PathVariable Integer id) {
        return CatalogConverter.convertCatalogToCatalogDto(catalogService.getCatalog(id));
    }

    @GetMapping("/{id}/childCatalogs")
    public List<CatalogDto> getListByParentCatalogId(@PathVariable Integer id) {
        return CatalogConverter.convertCatalogListToCatalogDtoList(catalogService.getListByParentCatalogId(id));
    }

    @GetMapping
    public List<CatalogDto> getList() {
        return CatalogConverter.convertCatalogListToCatalogDtoList(catalogService.getList());
    }

    @GetMapping(value = "/{id}/cars")
    public List<CarDto> getCarListByCatalogId(@PathVariable Integer id) {
        return CarConverter.convertCarListToCarDtoList(catalogService.getCarListByCatalogId(id));
    }
}
