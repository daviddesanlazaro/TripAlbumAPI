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
    public Mono<User> findUser(String id) {
        Mono<User> user = Mono.error(new UserNotFoundException());
        return userRepository.findById(id).switchIfEmpty(user);
    }

    @Override
    public Flux<User> findByUsername(String username) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        return userRepository.findByUsername(username).switchIfEmpty(userError);
    }

    @Override
    public Flux<User> findByPhone(String phone) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        return userRepository.findByPhone(phone).switchIfEmpty(userError);
    }

    @Override
    public Flux<User> findByUsernameAndPhone(String username, String phone) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        return userRepository.findByUsernameAndPhone(username, phone).switchIfEmpty(userError);
    }

    @Override
    public Mono<User> addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        userRepository.findById(id).switchIfEmpty(userError);
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> modifyUser(String id, User newUser) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        User user = userRepository.findById(id).switchIfEmpty(userError).block();

        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

        return userRepository.save(user);
    }

    @Override
    public Mono<User> patchUser(String id, String email) {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        User user = userRepository.findById(id).switchIfEmpty(userError).block();
        user.setEmail(email);
        return userRepository.save(user);
    }
}
