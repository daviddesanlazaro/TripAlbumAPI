package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUser(long id) throws UserNotFoundException;
    List<User> findAllUsers(boolean sendData, String name, String surname);

    User addUser(User user);
    User deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, User user) throws UserNotFoundException;
    User patchUser(long id, String email) throws UserNotFoundException;

    List<Visit> findVisitsUser(long userId) throws UserNotFoundException ;
}
