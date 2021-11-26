package com.svalero.tripalbumapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "visits")

public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-visit")
    private User user;
    @ManyToOne
    @JoinColumn(name = "place_id")
    @JsonBackReference(value = "place-visit")
    private Place place;
    @Column
    private LocalDate date;
    @Column
    private float rating;
    @Column
    private String comment;

}
