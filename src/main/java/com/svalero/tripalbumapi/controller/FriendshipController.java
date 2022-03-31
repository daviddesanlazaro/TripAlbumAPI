package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Friendship;
import com.svalero.tripalbumapi.domain.dto.FriendshipDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.FriendshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendshipController {

    private final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    FriendshipService friendshipService;

    // Mostrar todas las amistades
    @GetMapping("/friendships")
    public ResponseEntity<?> getFriendships() {
        logger.info("Start getFriendships");
        List<Friendship> friendships;

        friendships = friendshipService.findAllFriendships();
        logger.info("End getFriendships");
        return ResponseEntity.ok(friendships);
    }

    // Mostrar una favorito por ID
    @GetMapping("/friendship/{id}")
    public ResponseEntity<?> getFriendship(@PathVariable long id) throws FriendshipNotFoundException {
        logger.info("Start getFriendship " + id);
        Friendship friendship = friendshipService.findFriendship(id);
        logger.info("End getFriendship " + id);
        return ResponseEntity.ok(friendship);
    }

    // Eliminar una amistad
    @DeleteMapping("/friendship/{id}")
    public void removeFriendship(@PathVariable long id) throws FriendshipNotFoundException {
        logger.info("Start removeFriendship " + id);
        friendshipService.deleteFriendship(id);
        logger.info("End removeFriendship " + id);
    }

    // Insertar una amistad
    @PostMapping("/friendships")
    public ResponseEntity<?> addFriendship(@Valid @RequestBody FriendshipDTO friendshipDto) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start addFriendship");
        Friendship friendship = friendshipService.addFriendship(friendshipDto);
        logger.info("End addFriendship");
        return ResponseEntity.ok(friendship);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipNotFoundException(FriendshipNotFoundException fnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(fnfe.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(unfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
