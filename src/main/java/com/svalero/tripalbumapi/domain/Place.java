package com.svalero.tripalbumapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "places")

public class Place {

    @Id
    private String id;

    @Field
    @NotNull
    private String name;

    @Field
    @NotNull
    private String description;

    @Field
    @NotNull
    private float latitude;

    @Field
    @NotNull
    private float longitude;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @NotNull
    private Province province;

    @OneToMany(mappedBy = "place")
    @JsonBackReference(value = "place-visit")
    private List<Visit> visits;
}
