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
@Document(value = "countries")

public class Country {

    @Id
    private String id;

    @Field
    @NotNull
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonBackReference(value = "country-province")
    private List<Province> provinces;

}