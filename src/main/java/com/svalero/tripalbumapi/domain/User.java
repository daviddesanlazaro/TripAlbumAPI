package com.svalero.tripalbumapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")

public class User {

    @Id
    private String id;

    @Field
    @NotNull
    private String username;

    @Field
    @NotNull
    private String password;

    @Field
    @Email
    private String email;

    @Field
    @Pattern(regexp = "[0-9]{9}")
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-visit")
    private List<Visit> visits;
}
