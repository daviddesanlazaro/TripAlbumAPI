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
        return visitRepository.findById(id).onErrorReturn(new Visit());
    }

    @Override
    public Mono<Visit> addVisit(VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        Mono<User> user = userRepository.findById(visitDto.getUser()).onErrorReturn(new User());
        Mono<Place> place = placeRepository.findById(visitDto.getPlace()).onErrorReturn(new Place());

        ModelMapper mapper = new ModelMapper();
        Visit visit = mapper.map(visitDto, Visit.class);
        visit.setUser(user.block());
        visit.setPlace(place.block());
//        byte[] bytes = visitDto.getImage().getBytes(StandardCharsets.UTF_8);
//        visit.setImage(visitDto.getImage());
        return visitRepository.save(visit);
    }

    @Override
    public Mono<Void> deleteVisit(String id) throws VisitNotFoundException {
        Mono<Visit> visit = visitRepository.findById(id).onErrorReturn(new Visit());
        return visitRepository.deleteById(id);
    }

    @Override
    public Mono<Visit> modifyVisit(String id, VisitDTO visitDto) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        Mono<Visit> monoVisit = visitRepository.findById(id).onErrorReturn(new Visit());
        Mono<User> user = userRepository.findById(visitDto.getUser()).onErrorReturn(new User());
        Mono<Place> place = placeRepository.findById(visitDto.getPlace()).onErrorReturn(new Place());

        ModelMapper mapper = new ModelMapper();
        Visit visit = mapper.map(visitDto, Visit.class);
        visit.setId(id);
        visit.setUser(user.block());
        visit.setPlace(place.block());
        return visitRepository.save(visit);
    }

    @Override
    public Mono<Visit> patchVisit(String id, String commentary) throws VisitNotFoundException {
        Mono<Visit> monoVisit = visitRepository.findById(id).onErrorReturn(new Visit());
        Visit visit = monoVisit.block();
        visit.setCommentary(commentary);
        return visitRepository.save(visit);
    }

    @Override
    public Flux<Visit> findByUserAndPlace(String userId, String placeId) throws UserNotFoundException, PlaceNotFoundException {
        Mono<User> user = userRepository.findById(userId).onErrorReturn(new User());
        Mono<Place> place = placeRepository.findById(placeId).onErrorReturn(new Place());
        return visitRepository.findByUserAndPlace(user.block(), place.block());
    }
}
