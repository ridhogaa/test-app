package org.com.testbni.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountriesRequest {
    @NotNull
    private String name;

    @NotNull
    private String names;

    @NotNull
    private String capital;

    @NotNull
    private String flag;

    @NotNull
    private String code;

    @NotNull
    private String altCode;
}
