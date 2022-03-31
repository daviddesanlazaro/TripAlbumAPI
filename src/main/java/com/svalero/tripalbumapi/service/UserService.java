package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUser(long id) throws UserNotFoundException;
    List<User> findAllUsers(String name, String surname);

    User addUser(User user);
    void deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, User user) throws UserNotFoundException;
    User patchUser(long id, String email) throws UserNotFoundException;

    List<Place> findPlacesUser(User user) throws UserNotFoundException;
    List<Place> findFavoritePlacesUser(User user) throws UserNotFoundException;
    List<User> findFriendsUser(User user) throws UserNotFoundException;
}
