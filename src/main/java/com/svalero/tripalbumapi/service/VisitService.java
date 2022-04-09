package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VisitService {
    Flux<Visit> findAllVisits();
    Flux<Visit> findVisitsByUser(User user);
    Flux<Visit> findVisitsByPlace(Place place);
    Mono<Visit> findVisit(String id) throws VisitNotFoundException;

    Mono<Visit> addVisit(VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException;
    Mono<Void> deleteVisit(String id) throws VisitNotFoundException;
    Mono<Visit> modifyVisit(String id, VisitDTO visitDto) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException;
    Mono<Visit> patchVisit(String id, String commentary) throws VisitNotFoundException;

    Flux<Visit> findByUserAndPlace(String userId, String placeId) throws UserNotFoundException, PlaceNotFoundException;
}
