package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.*;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private PlaceService placeService;

    // Mostrar usuarios. Los parámetros permiten filtar por nombre o teléfono
    @GetMapping("/users")
    public ResponseEntity<Flux<User>> getUsers(@RequestParam(name = "username", required = false, defaultValue = "") String username,
                                               @RequestParam(name = "phone", required = false, defaultValue = "") String phone) {
        logger.info("Start getUsers");
        Flux<User> users;
        if (username.equals("") && (phone.equals(""))) {
            logger.info("Show all users");
            users = userService.findAllUsers();
        } else if (username.equals("")) {
            logger.info("Show users by phone");
            users = userService.findByPhone(phone);
        } else if (phone.equals("")) {
            logger.info("Show users by username");
            users = userService.findByUsername(username);
        } else {
            logger.info("Show users by username and phone");
            users = userService.findByUsernameAndPhone(username, phone);
        }
        logger.info("End getUsers");
        return ResponseEntity.ok(users);
    }

    // Mostrar un usuario por ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Mono<User>> getUser(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Start ShowUser " + userId);
        Mono<User> user = userService.findUser(userId);
        logger.info("End ShowUser " + userId);
        return ResponseEntity.ok(user);
    }

    // Eliminar un usuario
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Mono<Void>> deleteUser(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Start DeleteUser " + userId);
        Mono<Void> mono = userService.deleteUser(userId);
        logger.info("End DeleteUser " + userId);
        return ResponseEntity.ok(mono);
    }

    // Insertar un usuario
    @PostMapping("/users")
    public ResponseEntity<Mono<User>> addUser(@Valid @RequestBody User user) {
        logger.info("Start AddUser");
        Mono<User> newUser = userService.addUser(user);
        logger.info("End AddUser");
        return ResponseEntity.ok(newUser);
    }

    // Modificar un usuario
    @PutMapping("/user/{userId}")
    public ResponseEntity<Mono<User>> modifyUser(@Valid @RequestBody User user, @PathVariable String userId) throws UserNotFoundException {
        logger.info("Start ModifyUser " + userId);
        Mono<User> newUser = userService.modifyUser(userId, user);
        logger.info("End ModifyUser " + userId);
        return ResponseEntity.ok(newUser);
    }

    // Mostrar todas las visitas de un usuario
    @GetMapping("/user/{userId}/visits")
    public ResponseEntity<Flux<Visit>> getVisitsByUser(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Start getVisitsByUser");
        Flux<Visit> visits = null;
        logger.info("Search for user " + userId);
        Mono<User> user = userService.findUser(userId);
        logger.info("User found. Search for visits");
        visits = visitService.findVisitsByUser(user.block());
        logger.info("End getVisitsByUser");
        return ResponseEntity.ok(visits);
    }

    // Cambiar el email de un usuario
    @PatchMapping("/user/{userId}")
    public ResponseEntity<Mono<User>> patchUser(@PathVariable String userId, @Valid @RequestBody String email) throws UserNotFoundException {
        logger.info("Start PatchUser " + userId);
        Mono<User> user = userService.patchUser(userId, email);
        logger.info("End patchUser " + userId);
        return ResponseEntity.ok(user);
    }

    // Mostrar las visitas realizadas por un usuario determinado a un lugar determinado
    @GetMapping("/user/{userId}/place/{placeId}/visits")
    public ResponseEntity<Flux<Visit>> findByUserAndPlace(@PathVariable String userId, @PathVariable String placeId) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start findByUserAndPlace");
        Flux<Visit> visits = visitService.findByUserAndPlace(userId, placeId);
        logger.info("End findByUserAndPlace");
        return ResponseEntity.ok(visits);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        logger.info("400: Argument not valid");
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
        logger.info("400: Bad request");
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        logger.info("404: User not found");
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(unfe.getMessage()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        logger.info("404: Place not found");
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
