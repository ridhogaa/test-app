package org.com.testbni.service;

import org.com.testbni.dto.CountriesRequest;
import org.com.testbni.entity.Countries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountriesService {
    Page<Countries> getCountries(Pageable pageable, String fieldName, String capital, String code);

    Countries findById(String code);

    Countries save(CountriesRequest request);
}
