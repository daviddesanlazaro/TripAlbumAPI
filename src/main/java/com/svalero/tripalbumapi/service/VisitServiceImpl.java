package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.exception.VisitNotFoundException;
import com.svalero.tripalbumapi.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Flux<Visit> findAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Flux<Visit> findVisitsByUser(User user) {
        return visitRepository.findByUser(user);
    }

    @Override
    public Flux<Visit> findVisitsByPlace(Place place) {
        return visitRepository.findByPlace(place);
    }

    @Override
    public Mono<Visit> findVisit(String id) throws VisitNotFoundException {
        Mono<Visit> visitError = Mono.error(new VisitNotFoundException());
        return visitRepository.findById(id).switchIfEmpty(visitError);
    }

    @Override
    public Mono<Visit> addVisit(VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        Mono<User> monoUser = userRepository.findById(visitDto.getUser()).switchIfEmpty(userError);
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        Mono<Place> monoPlace = placeRepository.findById(visitDto.getPlace()).switchIfEmpty(placeError);

        ModelMapper mapper = new ModelMapper();
        Visit visit = mapper.map(visitDto, Visit.class);
        visit.setUser(monoUser.block());
        visit.setPlace(monoPlace.block());
        return visitRepository.save(visit);
    }

    @Override
    public Mono<Void> deleteVisit(String id) throws VisitNotFoundException {
        Mono<Visit> visit = Mono.error(new VisitNotFoundException());
        visitRepository.findById(id).switchIfEmpty(visit);
        return visitRepository.deleteById(id);
    }

    @Override
    public Mono<Visit> modifyVisit(String id, VisitDTO visitDto) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        Mono<Visit> visitError = Mono.error(new VisitNotFoundException());
        Mono<Visit> monoVisit = visitRepository.findById(id).switchIfEmpty(visitError);
        Mono<User> userError = Mono.error(new UserNotFoundException());
        Mono<User> monoUser = userRepository.findById(visitDto.getUser()).switchIfEmpty(userError);
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        Mono<Place> monoPlace = placeRepository.findById(visitDto.getPlace()).switchIfEmpty(placeError);

        if (!monoVisit.block().getId().equals(id))
            return visitError;

        ModelMapper mapper = new ModelMapper();
        Visit newVisit = mapper.map(visitDto, Visit.class);
        newVisit.setId(id);
        newVisit.setUser(monoUser.block());
        newVisit.setPlace(monoPlace.block());
        return visitRepository.save(newVisit);
    }

    @Override
    public Mono<Visit> patchVisit(String id, String commentary) throws VisitNotFoundException {
        Mono<Visit> visitError = Mono.error(new VisitNotFoundException());
        Visit visit = visitRepository.findById(id).switchIfEmpty(visitError).block();
        visit.setCommentary(commentary);
        return visitRepository.save(visit);
    }

    @Override
    public Flux<Visit> findByUserAndPlace(String userId, String placeId) throws UserNotFoundException, PlaceNotFoundException {
        Mono<User> userError = Mono.error(new UserNotFoundException());
        Mono<User> monoUser = userRepository.findById(userId).switchIfEmpty(userError);
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        Mono<Place> monoPlace = placeRepository.findById(placeId).switchIfEmpty(placeError);
        return visitRepository.findByUserAndPlace(monoUser.block(), monoPlace.block());
    }
}
