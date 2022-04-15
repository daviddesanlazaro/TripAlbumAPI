package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor

public class VisitDTO {
    @NotNull
    private String user;
    @NotNull
    private String place;
    @NotNull
    private LocalDate date;
    @Min(value = 0)
    @Max(value = 5)
    private float rating;
    private String commentary;
}
