package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.service.UserService;
import com.svalero.tripalbumapi.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VisitService visitService;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users;

        users = userService.findAllUsers();
        return users;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) throws UserNotFoundException {
        User user = userService.findUser(id);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public User removeUser(@PathVariable long id) throws UserNotFoundException {
        User user = userService.deleteUser(id);
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return newUser;
    }

    @PutMapping("/user/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable long id) throws UserNotFoundException {
        User newUser = userService.modifyUser(id, user);
        return newUser;
    }

    @GetMapping("/user/{userId}/visits")
    public List<Visit> getVisitsByUser(@PathVariable long userId) throws UserNotFoundException {
        List<Visit> visits = null;
        User user = userService.findUser(userId);
        visits = visitService.findVisitsByUser(user);
        return visits;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
