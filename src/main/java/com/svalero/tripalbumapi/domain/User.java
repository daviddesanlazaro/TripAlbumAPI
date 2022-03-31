package com.svalero.tripalbumapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String surname;

    @Column
    @Email
    private String email;

    @Column
    @Pattern(regexp = "[0-9]{9}")
    private String phone;

    @Column(name = "send_data")
    private boolean sendData;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-visit")
    private List<Visit> visits;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-favorite")
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-friend")
    private List<Friendship> friends;
}
