package com.svalero.tripalbumapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor

public class VisitDTO {
    private long user;
    private long place;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private float rating;
    private String commentary;
}
