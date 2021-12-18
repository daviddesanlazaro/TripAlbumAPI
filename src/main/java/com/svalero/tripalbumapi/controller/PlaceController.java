package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.service.PlaceService;
import com.svalero.tripalbumapi.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {

    private final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    private PlaceService placeService;
    @Autowired
    private VisitService visitService;

    // Mostrar todos los lugares
    @GetMapping("/places")
    public List<Place> getPlaces() {
        logger.info("Start getPlaces");
        List<Place> places;
        places = placeService.findAllPlaces();
        logger.info("End getPlaces");
        return places;
    }

    // Mostrar un lugar por ID
    @GetMapping("/place/{id}")
    public Place getPlace(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start ShowPlace " + id);
        Place place = placeService.findPlace(id);
        logger.info("End ShowPlace " + id);
        return place;
    }

    // Eliminar un lugar
    @DeleteMapping("/place/{id}")
    public Place removePlace(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start DeletePlace " + id);
        Place place = placeService.deletePlace(id);
        logger.info("End DeletePlace " + id);
        return place;
    }

    // Insertar un lugar
    @PostMapping("/places")
    public Place addPlace(@RequestBody PlaceDTO placeDto) throws ProvinceNotFoundException {
        logger.info("Start AddPlace");
        Place place = placeService.addPlace(placeDto);
        logger.info("End AddPlace");
        return place;
    }

    // Modificar un lugar
    @PutMapping("/place/{id}")
    public Place modifyPlace(@RequestBody PlaceDTO placeDto, @PathVariable long id) throws PlaceNotFoundException, ProvinceNotFoundException {
        logger.info("Start ModifyPlace " + id);
        Place newPlace = placeService.modifyPlace(id, placeDto);
        logger.info("End ModifyPlace " + id);
        return newPlace;
    }

    // Mostrar todas las visitas de un lugar
    @GetMapping("/place/{placeId}/visits")
    public List<Visit> getVisitsByPlace(@PathVariable long placeId) throws PlaceNotFoundException {
        logger.info("Start getVisitsByPlace");
        List<Visit> visits = null;
        logger.info("Search for place " + placeId);
        Place place = placeService.findPlace(placeId);
        logger.info("Place found. Search for visits");
        visits = visitService.findVisitsByPlace(place);
        logger.info("End getVisitsByPlace");
        return visits;
    }

    // Cambiar la descripción de un lugar
    @PatchMapping("/place/{id}")
    public Place patchPlace(@PathVariable long id, @RequestBody String description) throws PlaceNotFoundException {
        logger.info("Start PatchPlace " + id);
        Place place = placeService.patchPlace(id, description);
        logger.info("End patchPlace " + id);
        return place;
    }

    // Mostrar la valoración media de un lugar. SQL
    @GetMapping("/place/{id}/rating")
    public float averageRating(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start averageRating " + id);
        float rating = placeService.averageRating(id);
        logger.info("End averageRating " + id);
        return rating;
    }

    // Contar las visitas totales de un lugar. SQL
    @GetMapping("/place/{id}/numVisits")
    public int numVisits(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start numVisits " + id);
        int visits = placeService.numVisits(id);
        logger.info("End numVisits " + id);
        return visits;
    }

    // Contar los usuarios únicos que han visitado un lugar. SQL
    @GetMapping("/place/{id}/numUsers")
    public int numUsers(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start numUsers " + id);
        int users = placeService.numUsers(id);
        logger.info("End numUsers " + id);
        return users;
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        logger.info(pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        logger.info(pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
