package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.controller.UserController;
import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAllUsers(String name, String surname) {
        return userRepository.findByNameOrSurname(name, surname);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public User modifyUser(long id, User newUser) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setSendData(newUser.isSendData());

        return userRepository.save(user);
    }

    @Override
    public User patchUser(long id, String email) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public List<Place> findPlacesUser(User user) throws UserNotFoundException {
        user = userRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
        return userRepository.findPlacesUser(user);
    }

    @Override
    public List<Place> findFavoritePlacesUser(User user) throws UserNotFoundException {
        user = userRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
        return userRepository.findFavoritePlacesUser(user);
    }

    @Override
    public List<User> findFriendsUser(User user) throws UserNotFoundException {
        user = userRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
        return userRepository.findFriendsUser(user);
    }
}
