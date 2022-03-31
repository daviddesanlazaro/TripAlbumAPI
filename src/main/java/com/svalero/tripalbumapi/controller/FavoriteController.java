package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Favorite;
import com.svalero.tripalbumapi.domain.dto.FavoriteDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FavoriteController {

    private final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    FavoriteService favoriteService;

    // Mostrar todos los favoritos
    @GetMapping("/favorites")
    public ResponseEntity<?> getFavorites() {
        logger.info("Start getFavorites");
        List<Favorite> favorites;

        favorites = favoriteService.findAllFavorites();
        logger.info("End getFavorites");
        return ResponseEntity.ok(favorites);
    }

    // Mostrar una favorito por ID
    @GetMapping("/favorite/{id}")
    public ResponseEntity<?> getFavorite(@PathVariable long id) throws FavoriteNotFoundException {
        logger.info("Start getFavorite " + id);
        Favorite favorite = favoriteService.findFavorite(id);
        logger.info("End getFavorite " + id);
        return ResponseEntity.ok(favorite);
    }

    // Eliminar un favorito
    @DeleteMapping("/favorite/{id}")
    public void removeFavorite(@PathVariable long id) throws FavoriteNotFoundException {
        logger.info("Start removeFavorite " + id);
        favoriteService.deleteFavorite(id);
        logger.info("End removeFavorite " + id);
    }

    // Insertar un favorito
    @PostMapping("/favorites")
    public ResponseEntity<?> addFavorite(@Valid @RequestBody FavoriteDTO favoriteDto) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start addFavorite");
        Favorite favorite = favoriteService.addFavorite(favoriteDto);
        logger.info("End addFavorite");
        return ResponseEntity.ok(favorite);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFavoriteNotFoundException(FavoriteNotFoundException fnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(fnfe.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(unfe.getMessage()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
