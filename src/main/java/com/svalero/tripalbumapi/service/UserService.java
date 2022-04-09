package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> findAllUsers();
    Mono<User> findUser(String id) throws UserNotFoundException;
    Flux<User> findByUsername(String username);
    Flux<User> findByPhone(String phone);

    Mono<User> addUser(User user);
    Mono<Void> deleteUser(String id) throws UserNotFoundException;
    Mono<User> modifyUser(String id, User user) throws UserNotFoundException;
    Mono<User> patchUser(String id, String email) throws UserNotFoundException;
}
