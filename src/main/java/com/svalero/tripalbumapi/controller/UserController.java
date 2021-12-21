package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.service.UserService;
import com.svalero.tripalbumapi.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private VisitService visitService;

    // Mostrar usuarios. Los parámetros permiten filtar por nombre, apellido o email
    @GetMapping("/users")
    public List<User> getUsers(
            @RequestParam(name = "send_data", required = false) boolean sendData,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "view_all", defaultValue = "true") boolean viewAll) {

        logger.info("Start getUsers");
        List<User> users;
        if (viewAll) {
            logger.info("Show all users");
            users = userService.findAllUsers();
        } else {
            logger.info("Show users with params");
            users = userService.findAllUsers(sendData, name, surname);
        }
        logger.info("End getUsers");
        return users;
    }

    // Mostrar un usuario por ID
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start ShowUser " + id);
        User user = userService.findUser(id);
        logger.info("End ShowUser " + id);
        return user;
    }

    // Eliminar un usuario
    @DeleteMapping("/user/{id}")
    public User removeUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start DeleteUser " + id);
        User user = userService.deleteUser(id);
        logger.info("End DeleteUser " + id);
        return user;
    }

    // Insertar un usuario
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        logger.info("Start AddUser");
        User newUser = userService.addUser(user);
        logger.info("End AddUser");
        return newUser;
    }

    // Modificar un usuario
    @PutMapping("/user/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable long id) throws UserNotFoundException {
        logger.info("Start ModifyUser " + id);
        User newUser = userService.modifyUser(id, user);
        logger.info("End ModifyUser " + id);
        return newUser;
    }

    // Mostrar todas las visitas de un usuario
    @GetMapping("/user/{userId}/visits")
    public List<Visit> getVisitsByUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start getVisitsByUser");
        List<Visit> visits = null;
        logger.info("Search for user " + userId);
        User user = userService.findUser(userId);
        logger.info("User found. Search for visits");
        visits = visitService.findVisitsByUser(user);
        logger.info("End getVisitsByUser");
        return visits;
    }

    // Cambiar el email de un usuario
    @PatchMapping("/user/{id}")
    public User patchUser(@PathVariable long id, @RequestBody String email) throws UserNotFoundException {
        logger.info("Start PatchUser " + id);
        User user = userService.patchUser(id, email);
        logger.info("End patchUser " + id);
        return user;
    }

    // Mostrar los lugares que ha visitado un usuario. JPQL
    @GetMapping("/user/{userId}/places")
    public List<Place> findPlacesUser(@PathVariable long userId) throws UserNotFoundException {
        logger.info("Start findPlacesUser " + userId);
        User user = new User();
        user.setId(userId);
        logger.info("User created");
        List<Place> places = userService.findPlacesUser(user);
        logger.info("End findPlacesUser " + userId);
        return places;
    }

    // Mostrar las visitas realizadas por un usuario determinado a un lugar determinado
    @GetMapping("/user/place/visits")
    public List<Visit> findByUserAndPlace(@RequestBody VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start findByUserAndPlace");
        List<Visit> visits = visitService.findByUserAndPlace(visitDto);
        logger.info("End findByUserAndPlace");
        return visits;
    }

    // Eliminar todas las visitas de un usuario con menor valoración que la determinada. JPQL
    @DeleteMapping("user/visits")
    public void deleteByUserDate(@RequestBody VisitDTO visitDto) throws UserNotFoundException {
        logger.info("Start deleteByUserRating");
        visitService.deleteByUserRating(visitDto);
        logger.info("End deleteByUserRating");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", unfe.getMessage());
        logger.info(unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
