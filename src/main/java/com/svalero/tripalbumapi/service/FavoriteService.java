package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Favorite;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.dto.FavoriteDTO;
import com.svalero.tripalbumapi.exception.FavoriteNotFoundException;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findAllFavorites();
    List<Favorite> findFavorites(User user);
    Favorite findFavorite(long id) throws FavoriteNotFoundException;
    Favorite addFavorite(FavoriteDTO favoriteDto) throws UserNotFoundException, PlaceNotFoundException;
    void deleteFavorite(long id) throws FavoriteNotFoundException;
}
