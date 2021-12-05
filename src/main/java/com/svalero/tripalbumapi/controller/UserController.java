package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.ErrorResponse;
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

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start ShowUser " + id);
        User user = userService.findUser(id);
        logger.info("End ShowUser " + id);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public User removeUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("Start DeleteUser " + id);
        User user = userService.deleteUser(id);
        logger.info("End DeleteUser " + id);
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        logger.info("Start AddUser");
        User newUser = userService.addUser(user);
        logger.info("End AddUser");
        return newUser;
    }

    @PutMapping("/user/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable long id) throws UserNotFoundException {
        logger.info("Start ModifyUser " + id);
        User newUser = userService.modifyUser(id, user);
        logger.info("End ModifyUser " + id);
        return newUser;
    }

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
