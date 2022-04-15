package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.*;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VisitController {

    private final Logger logger = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private VisitService visitService;

    // Mostrar todas las visitas
    @GetMapping("/visits")
    public ResponseEntity<Flux<Visit>> getVisits() {
        logger.info("Start getVisits");
        Flux<Visit> visits;

        visits = visitService.findAllVisits();
        logger.info("End getVisits");
        return ResponseEntity.ok(visits);
    }

    // Mostrar una visita por ID
    @GetMapping("/visit/{visitId}")
    public ResponseEntity<Mono<Visit>> getVisit(@PathVariable String visitId) throws VisitNotFoundException {
        logger.info("Start ShowVisit " + visitId);
        Mono<Visit> visit = visitService.findVisit(visitId);
        logger.info("End ShowVisit " + visitId);
        return ResponseEntity.ok(visit);
    }

    // Eliminar una visita
    @DeleteMapping("/visit/{visitId}")
    public ResponseEntity<Mono<Void>> deleteVisit(@PathVariable String visitId) throws VisitNotFoundException {
        logger.info("Start DeleteVisit " + visitId);
        Mono<Void> mono = visitService.deleteVisit(visitId);
        logger.info("End DeleteVisit " + visitId);
        return ResponseEntity.ok(mono);
    }

    // Insertar una visita
    @PostMapping("/visits")
    public ResponseEntity<Mono<Visit>> addVisit(@Valid @RequestBody VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException, IOException {
        logger.info("Start AddVisit");
        Mono<Visit> visit = visitService.addVisit(visitDto);
        logger.info("End AddVisit");
        return ResponseEntity.ok(visit);
    }

    // Modificar una visita
    @PutMapping("/visit/{visitId}")
    public ResponseEntity<Mono<Visit>> modifyVisit(@Valid @RequestBody VisitDTO visitDto, @PathVariable String visitId) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        logger.info("Start ModifyVisit " + visitId);
        if (visitDto.getCommentary() == null)
            visitDto.setCommentary("");

        Mono<Visit> newVisit = visitService.modifyVisit(visitId, visitDto);
        logger.info("End ModifyVisit " + visitId);
        return ResponseEntity.ok(newVisit);
    }

    // Cambiar el comentario de una visita. JPQL
    @PatchMapping("/visit/{visitId}")
    public ResponseEntity<Mono<Visit>> patchVisit(@PathVariable String visitId, @RequestBody String commentary) throws VisitNotFoundException {
        logger.info("Start PatchVisit " + visitId);
        Mono<Visit> visit = visitService.patchVisit(visitId, commentary);
        logger.info("End patchVisit " + visitId);
        return ResponseEntity.ok(visit);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        logger.info("400: Argument not valid");
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
        logger.info("400: Bad request");
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVisitNotFoundException(VisitNotFoundException vnfe) {
        logger.info("404: Visit not found");
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(vnfe.getMessage()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        logger.info("404: Place not found");
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        logger.info("404: User not found");
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(unfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
