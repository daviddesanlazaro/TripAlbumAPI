package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findAll();
    Flux<User> findByUsername(String username);
    Flux<User> findByPhone(String phone);
    Flux<User> findByUsernameAndPhone(String username, String phone);
}
