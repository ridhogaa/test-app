package org.com.testbni.service;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.com.testbni.dto.CountriesRequest;
import org.com.testbni.entity.Countries;
import org.com.testbni.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CountriesServiceImpl implements CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private Validator validator;

    @Override
    public Page<Countries> getCountries(Pageable pageable, String fieldName, String capital, String code) {
        Specification<Countries> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fieldName != null) {
                predicates.add(criteriaBuilder.equal(root.get("name"), fieldName));
            }

            if (capital != null) {
                predicates.add(criteriaBuilder.equal(root.get("capital"), capital));
            }

            if (code != null) {
                predicates.add(criteriaBuilder.equal(root.get("code"), code));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Page<Countries> countries = countriesRepository.findAll(spec, pageable);
        log.info("Found {} countries", countries.getContent());

        return new PageImpl<>(countries.getContent(), pageable, countries.getTotalElements());

    }

    @Override
    public Countries findById(String code) {
        return countriesRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Countries not found"));
    }

    @Override
    public Countries save(CountriesRequest request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        if (countriesRepository.findByCode(request.getCode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Countries already exists");
        }
        return countriesRepository.save(Countries.builder()
                .name(request.getName())
                .capital(request.getCapital())
                .names(request.getNames())
                .altCode(request.getAltCode())
                .flag(request.getFlag())
                .code(request.getCode())
                .createdAt(LocalDateTime.now())
                .build());
    }
}
