package com.svalero.tripalbumapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "visits")

public class Visit {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    @NotNull
    private Place place;

    @Field
    @NotNull
    private LocalDate date;

    @Field
    @Min(value = 0)
    @Max(value = 10)
    private float rating;

    @Field
    private String commentary;

    @Field
    private String image;
}
