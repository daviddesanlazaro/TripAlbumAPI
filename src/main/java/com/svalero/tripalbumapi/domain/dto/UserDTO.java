package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor

public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "[0-9]{9}")
    private String phone;
}
