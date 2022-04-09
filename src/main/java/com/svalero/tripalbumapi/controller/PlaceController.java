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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
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
    public ResponseEntity<Flux<Place>> getPlaces(@RequestParam (name = "name", required = false, defaultValue = "") String name) {
        logger.info("Start getPlaces");
        Flux<Place> places;
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
    @GetMapping("/place/{placeId}")
    public ResponseEntity<Mono<Place>> getPlace(@PathVariable String placeId) throws PlaceNotFoundException {
        logger.info("Start ShowPlace " + placeId);
        Mono<Place> place = placeService.findPlace(placeId);
        logger.info("End ShowPlace " + placeId);
        return ResponseEntity.ok(place);
    }

    // Eliminar un lugar
    @DeleteMapping("/place/{placeId}")
    public ResponseEntity<Mono<Void>> deletePlace(@PathVariable String placeId) throws PlaceNotFoundException {
        logger.info("Start DeletePlace " + placeId);
        Mono<Void> mono = placeService.deletePlace(placeId);
        logger.info("End DeletePlace " + placeId);
        return ResponseEntity.ok(mono);
    }

    // Insertar un lugar
    @PostMapping("/places")
    public ResponseEntity<Mono<Place>> addPlace(@Valid @RequestBody PlaceDTO placeDto) throws ProvinceNotFoundException {
        logger.info("Start AddPlace");
        Mono<Place> place = placeService.addPlace(placeDto);
        logger.info("End AddPlace");
        return ResponseEntity.ok(place);
    }

    // Modificar un lugar
    @PutMapping("/place/{placeId}")
    public ResponseEntity<Mono<Place>> modifyPlace(@Valid @RequestBody PlaceDTO placeDto, @PathVariable String placeId) throws PlaceNotFoundException, ProvinceNotFoundException {
        logger.info("Start ModifyPlace " + placeId);
        Mono<Place> newPlace = placeService.modifyPlace(placeId, placeDto);
        logger.info("End ModifyPlace " + placeId);
        return ResponseEntity.ok(newPlace);
    }

    // Mostrar todas las visitas de un lugar
    @GetMapping("/place/{placeId}/visits")
    public ResponseEntity<Flux<Visit>> getVisitsByPlace(@PathVariable String placeId) throws PlaceNotFoundException {
        logger.info("Start getVisitsByPlace");
        Flux<Visit> visits = null;
        logger.info("Search for place " + placeId);
        Mono<Place> place = placeService.findPlace(placeId);
        logger.info("Place found. Search for visits");
        visits = visitService.findVisitsByPlace(place.block());
        logger.info("End getVisitsByPlace");
        return ResponseEntity.ok(visits);
    }

    // Cambiar la descripción de un lugar
    @PatchMapping("/place/{placeId}")
    public ResponseEntity<Mono<Place>> patchPlace(@PathVariable String placeId, @Valid @RequestBody String description) throws PlaceNotFoundException {
        logger.info("Start PatchPlace " + placeId);
        Mono<Place> place = placeService.patchPlace(placeId, description);
        logger.info("End patchPlace " + placeId);
        return ResponseEntity.ok(place);
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
