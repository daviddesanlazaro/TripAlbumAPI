package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUser(long id) throws UserNotFoundException;

    User addUser(User user);
    User deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, User user) throws UserNotFoundException;
}
