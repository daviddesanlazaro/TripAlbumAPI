package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findUser(String id) throws UserNotFoundException {
        return userRepository.findById(id).onErrorReturn(new User());
    }

    @Override
    public Flux<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Flux<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone).onErrorReturn(new User());
    }

    @Override
    public Mono<User> addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(String id) throws UserNotFoundException {
        Mono<User> user = userRepository.findById(id).onErrorReturn(new User());
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> modifyUser(String id, User newUser) throws UserNotFoundException {
        Mono<User> monoUser = userRepository.findById(id).onErrorReturn(new User());

        User user = monoUser.block();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

        return userRepository.save(user);
    }

    @Override
    public Mono<User> patchUser(String id, String email) throws UserNotFoundException {
        Mono<User> monoUser = userRepository.findById(id).onErrorReturn(new User());
        User user = monoUser.block();
        user.setEmail(email);
        return userRepository.save(user);
    }
}
