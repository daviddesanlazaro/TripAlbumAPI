package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VisitRepository extends ReactiveMongoRepository<Visit, String> {
    Flux<Visit> findAll();
    Flux<Visit> findByUser(User user);
    Flux<Visit> findByPlace(Place place);
    Flux<Visit> findByUserAndPlace(User user, Place place);

}
