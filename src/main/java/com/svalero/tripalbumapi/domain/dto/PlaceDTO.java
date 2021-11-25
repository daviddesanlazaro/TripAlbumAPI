package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PlaceDTO {

    private String name;
    private String description;
    private float latitude;
    private float longitude;
    private long province;

}
