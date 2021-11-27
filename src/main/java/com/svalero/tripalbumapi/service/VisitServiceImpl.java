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

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<Visit> findAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public List<Visit> findVisitsByUser(User user) {
        return visitRepository.findByUser(user);
    }

    @Override
    public List<Visit> findVisitsByPlace(Place place) {
        return visitRepository.findByPlace(place);
    }

    @Override
    public Visit findVisit(long id) throws VisitNotFoundException {
        return visitRepository.findById(id)
                .orElseThrow(VisitNotFoundException::new);
    }

    @Override
    public Visit addVisit(VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        User user = userRepository.findById(visitDto.getUser())
                .orElseThrow(UserNotFoundException::new);
        Place place = placeRepository.findById(visitDto.getPlace())
                .orElseThrow(PlaceNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Visit visit = mapper.map(visitDto, Visit.class);
        visit.setUser(user);
        visit.setPlace(place);
        return visitRepository.save(visit);
    }

    @Override
    public Visit deleteVisit(long id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(VisitNotFoundException::new);
        visitRepository.delete(visit);
        return visit;
    }

    @Override
    public Visit modifyVisit(long id, VisitDTO newVisitDto) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(VisitNotFoundException::new);
        User user = userRepository.findById(newVisitDto.getUser())
                .orElseThrow(UserNotFoundException::new);
        Place place = placeRepository.findById(newVisitDto.getPlace())
                .orElseThrow(PlaceNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        visit = mapper.map(newVisitDto, Visit.class);
        visit.setId(id);
        visit.setUser(user);
        visit.setPlace(place);
        return visitRepository.save(visit);
    }
}