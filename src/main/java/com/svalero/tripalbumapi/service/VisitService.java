package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.*;

import java.util.List;

public interface VisitService {
    List<Visit> findAllVisits();
    List<Visit> findVisitsByUser(User user);
    List<Visit> findVisitsByPlace(Place place);
    Visit findVisit(long id) throws VisitNotFoundException;

    Visit addVisit(VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException;
    void deleteVisit(long id) throws VisitNotFoundException;
    Visit modifyVisit(long id, VisitDTO visitDto) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException;
    Visit patchVisit(long id, String commentary) throws VisitNotFoundException;

    List<Visit> findByUserAndPlace(long userId, long placeId) throws UserNotFoundException, PlaceNotFoundException;
}
