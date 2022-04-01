package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.PlaceService;
import com.svalero.tripalbumapi.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PlaceController {

    private final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    private PlaceService placeService;
    @Autowired
    private VisitService visitService;

    // Mostrar todos los lugares
    @GetMapping("/places")
    public ResponseEntity<?> getPlaces(@RequestParam (name = "name", required = false, defaultValue = "") String name) {
        logger.info("Start getPlaces");
        List<Place> places;
        if (name.equals("")) {
            logger.info("Show all places");
            places = placeService.findAllPlaces();
        } else {
            logger.info("Show places with search");
            places = placeService.findBySearch(name);
        }
        logger.info("End getPlaces");
        return ResponseEntity.ok(places);
    }

    // Mostrar un lugar por ID
    @GetMapping("/place/{id}")
    public ResponseEntity<?> getPlace(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start ShowPlace " + id);
        Place place = placeService.findPlace(id);
        logger.info("End ShowPlace " + id);
        return ResponseEntity.ok(place);
    }

    // Eliminar un lugar
    @DeleteMapping("/place/{id}")
    public void removePlace(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start DeletePlace " + id);
        placeService.deletePlace(id);
        logger.info("End DeletePlace " + id);
    }

    // Insertar un lugar
    @PostMapping("/places")
    public ResponseEntity<?> addPlace(@Valid @RequestBody PlaceDTO placeDto) throws ProvinceNotFoundException {
        logger.info("Start AddPlace");
        Place place = placeService.addPlace(placeDto);
        logger.info("End AddPlace");
        return ResponseEntity.ok(place);
    }

    // Modificar un lugar
    @PutMapping("/place/{id}")
    public ResponseEntity<?> modifyPlace(@Valid @RequestBody PlaceDTO placeDto, @PathVariable long id) throws PlaceNotFoundException, ProvinceNotFoundException {
        logger.info("Start ModifyPlace " + id);
        Place newPlace = placeService.modifyPlace(id, placeDto);
        logger.info("End ModifyPlace " + id);
        return ResponseEntity.ok(newPlace);
    }

    // Mostrar todas las visitas de un lugar
    @GetMapping("/place/{placeId}/visits")
    public ResponseEntity<?> getVisitsByPlace(@PathVariable long placeId) throws PlaceNotFoundException {
        logger.info("Start getVisitsByPlace");
        List<Visit> visits = null;
        logger.info("Search for place " + placeId);
        Place place = placeService.findPlace(placeId);
        logger.info("Place found. Search for visits");
        visits = visitService.findVisitsByPlace(place);
        logger.info("End getVisitsByPlace");
        return ResponseEntity.ok(visits);
    }

    // Cambiar la descripción de un lugar
    @PatchMapping("/place/{id}")
    public ResponseEntity<?> patchPlace(@PathVariable long id, @Valid @RequestBody String description) throws PlaceNotFoundException {
        logger.info("Start PatchPlace " + id);
        Place place = placeService.patchPlace(id, description);
        logger.info("End patchPlace " + id);
        return ResponseEntity.ok(place);
    }

    // Mostrar la valoración media de un lugar. SQL
    @GetMapping("/place/{id}/rating")
    public ResponseEntity<?> averageRating(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start averageRating " + id);
        float rating = placeService.averageRating(id);
        logger.info("End averageRating " + id);
        return ResponseEntity.ok(rating);
    }

    // Contar las visitas totales de un lugar. SQL
    @GetMapping("/place/{id}/numVisits")
    public ResponseEntity<?> numVisits(@PathVariable long id) throws PlaceNotFoundException {
        logger.info("Start numVisits " + id);
        int visits = placeService.numVisits(id);
        logger.info("End numVisits " + id);
        return ResponseEntity.ok(visits);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
