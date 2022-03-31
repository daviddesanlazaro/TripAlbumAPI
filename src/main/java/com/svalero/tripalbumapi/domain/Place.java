package com.svalero.tripalbumapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Place")
@Table(name = "places")

public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private float latitude;

    @Column
    @NotNull
    private float longitude;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @NotNull
    private Province province;

    @OneToMany(mappedBy = "place")
    @JsonBackReference(value = "place-visit")
    private List<Visit> visits;

    @OneToMany(mappedBy = "place")
    @JsonBackReference(value = "place-favorite")
    private List<Favorite> favorites;
}
