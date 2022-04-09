package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor

public class PlaceDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private float latitude;
    @NotNull
    private float longitude;
    @NotNull
    private String province;
}
