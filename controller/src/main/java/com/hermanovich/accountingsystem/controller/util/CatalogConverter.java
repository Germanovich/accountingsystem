package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.catalog.CatalogDto;
import com.hermanovich.accountingsystem.model.catalog.Catalog;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CatalogConverter {

    public CatalogDto convertCatalogToCatalogDto(Catalog catalog) {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(catalog.getId());
        catalogDto.setName(catalog.getName());
        if (catalog.getParentCatalog() != null) {
            catalogDto.setParentCatalog(convertCatalogToCatalogDto(catalog.getParentCatalog()));
        }
        return catalogDto;
    }

    public Catalog convertCatalogDtoToCatalog(CatalogDto catalogDto) {
        if (catalogDto.getParentCatalog() == null || catalogDto.getParentCatalog().getId() == null) {
            catalogDto.setParentCatalog(null);
        }
        return Catalog.builder()
                .id(catalogDto.getId())
                .name(catalogDto.getName())
                .parentCatalog(catalogDto.getParentCatalog() == null ?
                        null : Catalog.builder().id(catalogDto.getParentCatalog().getId()).build())
                .build();
    }

    public List<CatalogDto> convertCatalogListToCatalogDtoList(List<Catalog> catalogList) {
        List<CatalogDto> catalogDtoList = new ArrayList<>();
        for (Catalog catalog : catalogList) {
            catalogDtoList.add(convertCatalogToCatalogDto(catalog));
        }
        return catalogDtoList;
    }
}
