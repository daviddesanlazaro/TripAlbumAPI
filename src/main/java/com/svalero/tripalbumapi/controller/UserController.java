package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.*;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.FavoriteService;
import com.svalero.tripalbumapi.service.FriendshipService;
import com.svalero.tripalbumapi.service.UserService;
import com.svalero.tripalbumapi.service.VisitService;
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
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private FriendshipService friendshipService;

    // Mostrar usuarios. Los parámetros permiten filtar por nombre o apellido
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        logger.info("Start getUsers");
        List<User> users;
        if (name.equals("")) {
            logger.info("Show all users");
            users = userService.findAllUsers();
        } else {
            logger.info("Show users by name");
            users = userService.findByName(name);
            logger.info("End getUsers");
        }
        return ResponseEntity.ok(users);
    }

    // Buscar usuario por teléfono para añadir a amigos
    @GetMapping("/user/{userId}/searchForFriend")
    public ResponseEntity<?> findNewFriend(@PathVariable long userId, @RequestParam(name = "phone") String phone) throws UserNotFoundException {
        logger.info("Start findNewFriend");
        List<User> users;
        users = userService.findNewFriend(userId, phone);
        logger.info("End findNewFriend");
        return ResponseEntity.ok(users);
    }

    // Mostrar un usuario por ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start ShowUser " + id);
        User user = userService.findUser(id);
        logger.info("End ShowUser " + id);
        return ResponseEntity.ok(user);
    }

    // Eliminar un usuario
    @DeleteMapping("/user/{id}")
    public void removeUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start DeleteUser " + id);
        userService.deleteUser(id);
        logger.info("End DeleteUser " + id);
    }

    // Insertar un usuario
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        logger.info("Start AddUser");
        User newUser = userService.addUser(user);
        logger.info("End AddUser");
        return ResponseEntity.ok(newUser);
    }

    // Modificar un usuario
    @PutMapping("/user/{id}")
    public ResponseEntity<?> modifyUser(@Valid @RequestBody User user, @PathVariable long id) throws UserNotFoundException {
        logger.info("Start ModifyUser " + id);
        User newUser = userService.modifyUser(id, user);
        logger.info("End ModifyUser " + id);
        return ResponseEntity.ok(newUser);
    }

    // Mostrar todas las visitas de un usuario
    @GetMapping("/user/{userId}/visits")
    public ResponseEntity<?> getVisitsByUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start getVisitsByUser");
        List<Visit> visits = null;
        logger.info("Search for user " + userId);
        User user = userService.findUser(userId);
        logger.info("User found. Search for visits");
        visits = visitService.findVisitsByUser(user);
        logger.info("End getVisitsByUser");
        return ResponseEntity.ok(visits);
    }

    // Cambiar el email de un usuario
    @PatchMapping("/user/{id}")
    public ResponseEntity<?> patchUser(@PathVariable long id, @Valid @RequestBody String email) throws UserNotFoundException {
        logger.info("Start PatchUser " + id);
        User user = userService.patchUser(id, email);
        logger.info("End patchUser " + id);
        return ResponseEntity.ok(user);
    }

    // Mostrar los lugares que ha visitado un usuario. JPQL
    @GetMapping("/user/{userId}/places")
    public ResponseEntity<?> findPlacesUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start findPlacesUser " + userId);
        User user = new User();
        user.setId(userId);
        logger.info("User created");
        List<Place> places = userService.findPlacesUser(user);
        logger.info("End findPlacesUser " + userId);
        return ResponseEntity.ok(places);
    }

    // Mostrar los lugares favoritos de un usuario. JPQL
    @GetMapping("/user/{userId}/favoritePlaces")
    public ResponseEntity<?> findFavoritePlacesUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start findFavoritePlacesUser " + userId);
        User user = new User();
        user.setId(userId);
        logger.info("User created");
        List<Place> places = userService.findFavoritePlacesUser(user);
        logger.info("End findFavoritePlacesUser " + userId);
        return ResponseEntity.ok(places);
    }

    // Mostrar los lugares favoritos de un usuario. JPQL
    @GetMapping("/user/{userId}/friends")
    public ResponseEntity<?> findFriendsUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start findFriendsUser " + userId);
        User user = new User();
        user.setId(userId);
        logger.info("User created");
        List<User> users = userService.findFriendsUser(user);
        logger.info("End findFriendsUser " + userId);
        return ResponseEntity.ok(users);
    }

    // Mostrar las visitas realizadas por un usuario determinado a un lugar determinado
    @GetMapping("/user/{userId}/place/{placeId}/visits")
    public ResponseEntity<?> findByUserAndPlace(@PathVariable long userId, @PathVariable long placeId) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start findByUserAndPlace");
        List<Visit> visits = visitService.findByUserAndPlace(userId, placeId);
        logger.info("End findByUserAndPlace");
        return ResponseEntity.ok(visits);
    }

    // Mostrar todos los favoritos por usuario
    @GetMapping("/user/{userId}/favorites")
    public ResponseEntity<?> getFavoritesByUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start getFavoritesByUser");
        List<Favorite> favorites = null;
        logger.info("Search for user " + userId);
        User user = userService.findUser(userId);
        logger.info("User found. Search for favorites");
        favorites = favoriteService.findFavorites(user);
        logger.info("End getFavoritesByUser");
        return ResponseEntity.ok(favorites);
    }

    // Mostrar todas las amistades por usuario
    @GetMapping("/user/{userId}/friendships")
    public ResponseEntity<?> getFrienshipsByUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start getFrienshipsByUser");
        List<Friendship> friendships = null;
        logger.info("Search for user " + userId);
        User user = userService.findUser(userId);
        logger.info("User found. Search for favorites");
        friendships = friendshipService.findByUser(user);
        logger.info("End getFrienshipsByUser");
        return ResponseEntity.ok(friendships);
    }

    // Mostrar la amistad de un usuario y su amigo
    @GetMapping("/user/{userId}/user/{friendId}/friendship")
    public ResponseEntity<?> getFriendshipByUserAndFriend(@PathVariable long userId, @PathVariable long friendId) throws UserNotFoundException {
        logger.info("Start getFriendshipByUserAndFriend");
        logger.info("Search for user " + userId);
        User user = userService.findUser(userId);
        logger.info("User found. Search for friend" + friendId);
        User friend = userService.findUser(friendId);
        logger.info("Friend found. Search for friendship");
        Friendship friendship = friendshipService.findByUserAndFriend(user, friend);
        logger.info("End getFriendshipByUserAndFriend");
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
