package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.service.PlaceService;
import com.svalero.tripalbumapi.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private VisitService visitService;

    @GetMapping("/places")
    public List<Place> getPlaces() {
        List<Place> places;
        places = placeService.findAllPlaces();
        return places;
    }

    @GetMapping("/place/{id}")
    public Place getPlace(@PathVariable long id) throws PlaceNotFoundException {
        Place place = placeService.findPlace(id);
        return place;
    }

    @DeleteMapping("/place/{id}")
    public Place removePlace(@PathVariable long id) throws PlaceNotFoundException {
        Place place = placeService.deletePlace(id);
        return place;
    }

    @PostMapping("/places")
    public Place addPlace(@RequestBody PlaceDTO placeDto) throws ProvinceNotFoundException {
        Place place = placeService.addPlace(placeDto);
        return place;
    }

    @PutMapping("/place/{id}")
    public Place modifyPlace(@RequestBody PlaceDTO placeDto, @PathVariable long id) throws PlaceNotFoundException, ProvinceNotFoundException {
        Place newPlace = placeService.modifyPlace(id, placeDto);
        return newPlace;
    }
    @GetMapping("/place/{placeId}/visits")
    public List<Visit> getVisitsByPlace(@PathVariable long placeId) throws PlaceNotFoundException {
        List<Visit> visits = null;
        Place place = placeService.findPlace(placeId);
        visits = visitService.findVisitsByPlace(place);
        return visits;
    }


    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
