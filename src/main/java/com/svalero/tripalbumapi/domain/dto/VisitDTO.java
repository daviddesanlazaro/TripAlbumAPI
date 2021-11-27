package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor

public class VisitDTO {
    private long user;
    private long place;
    private LocalDate date;
    private float rating;
    private String comment;
}
