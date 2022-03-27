package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor

public class ProvinceDTO {
    @NotNull
    private String name;
    @NotNull
    private long country;
}
