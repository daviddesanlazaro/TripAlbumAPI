package com.svalero.tripalbumapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor

public class FavoriteDTO {
    @NotNull
    private long user;
    @NotNull
    private long place;
}
