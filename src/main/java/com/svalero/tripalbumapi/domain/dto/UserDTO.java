package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private boolean sendData;
}
