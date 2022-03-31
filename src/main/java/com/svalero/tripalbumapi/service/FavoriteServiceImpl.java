package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Favorite;
import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.dto.FavoriteDTO;
import com.svalero.tripalbumapi.exception.FavoriteNotFoundException;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.repository.FavoriteRepository;
import com.svalero.tripalbumapi.repository.PlaceRepository;
import com.svalero.tripalbumapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    @Override
    public List<Favorite> findFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    @Override
    public Favorite findFavorite(long id) throws FavoriteNotFoundException {
        return favoriteRepository.findById(id)
                .orElseThrow(FavoriteNotFoundException::new);
    }

    @Override
    public Favorite addFavorite(FavoriteDTO favoriteDto) throws UserNotFoundException, PlaceNotFoundException {
        User user = userRepository.findById(favoriteDto.getUser())
                .orElseThrow(UserNotFoundException::new);
        Place place = placeRepository.findById(favoriteDto.getPlace())
                .orElseThrow(PlaceNotFoundException::new);

        Favorite favorite = new Favorite(0, user, place);
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(long id) throws FavoriteNotFoundException {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(FavoriteNotFoundException::new);
        favoriteRepository.delete(favorite);
    }
}
