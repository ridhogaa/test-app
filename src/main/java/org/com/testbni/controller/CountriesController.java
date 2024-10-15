package org.com.testbni.controller;

import org.com.testbni.dto.BaseResponse;
import org.com.testbni.dto.CountriesRequest;
import org.com.testbni.dto.PagingResponse;
import org.com.testbni.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/countries")
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @GetMapping
    public ResponseEntity<?> getCountries(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(required = false, name = "fieldName") String fieldName,
            @RequestParam(required = false, name = "capital") String capital,
            @RequestParam(required = false, name = "code") String code
    ) {
        var contents = countriesService.getCountries(PageRequest.of(page, size), fieldName, capital, code);
        return ResponseEntity.ok(BaseResponse.success(
                PagingResponse.builder()
                        .contents(contents.getContent())
                        .totalPage(contents.getTotalPages())
                        .currentPage(contents.getNumber())
                        .totalItem((int) contents.getTotalElements())
                        .size(contents.getSize())
                        .build(), "Countries retrieved successfully"));
    }

    @GetMapping("{code}")
    public ResponseEntity<?> findByCode(@PathVariable(name = "code") String code) {
        return ResponseEntity.ok(BaseResponse.success(countriesService.findById(code), "Countries by code retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<?> createCountries(@RequestBody CountriesRequest countries) {
        return ResponseEntity.ok(BaseResponse.success(countriesService.save(countries), "Countries created successfully"));
    }
}
