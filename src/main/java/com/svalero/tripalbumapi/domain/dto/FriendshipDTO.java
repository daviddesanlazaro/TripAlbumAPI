package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor

public class FriendshipDTO {
    @NotNull
    private long user;
    @NotNull
    private long friend;
}
