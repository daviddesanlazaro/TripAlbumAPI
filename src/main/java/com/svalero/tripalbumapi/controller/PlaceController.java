package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/places")
    public List<Place> getPlacesByProvinceId(@RequestParam(name = "province", defaultValue = "0") long provinceId) {
        List<Place> places;

        if (provinceId == 0) {
            places = placeService.findAllPlaces();
        } else {
            places = placeService.findAllPlaces(provinceId);
        }
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
    public Place addPlace(@RequestBody Place place) {
        Place newPlace = placeService.addPlace(place);
        return newPlace;
    }

    @PutMapping("/place/{id}")
    public Place modifyPlace(@RequestBody Place place, @PathVariable long id) throws PlaceNotFoundException {
        Place newPlace = placeService.modifyPlace(id, place);
        return newPlace;
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
