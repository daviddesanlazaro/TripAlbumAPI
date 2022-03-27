package com.svalero.tripalbumapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private long user;
    @NotNull
    private long place;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate date;
    @Min(value = 0)
    @Max(value = 10)
    private float rating;
    private String commentary;
}
