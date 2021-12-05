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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VisitController {

    private final Logger logger = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private VisitService visitService;

    @GetMapping("/visits")
    public List<Visit> getVisits() {
        logger.info("Start getVisits");
        List<Visit> visits;

        visits = visitService.findAllVisits();
        logger.info("End getVisits");
        return visits;
    }

    @GetMapping("/visit/{id}")
    public Visit getVisit(@PathVariable long id) throws VisitNotFoundException {
        logger.info("Start ShowVisit " + id);
        Visit visit = visitService.findVisit(id);
        logger.info("End ShowVisit " + id);
        return visit;
    }

    @DeleteMapping("/visit/{id}")
    public Visit removeVisit(@PathVariable long id) throws VisitNotFoundException {
        logger.info("Start DeleteVisit " + id);
        Visit visit = visitService.deleteVisit(id);
        logger.info("End DeleteVisit " + id);
        return visit;
    }

    @PostMapping("/visits")
    public Visit addVisit(@RequestBody VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start AddVisit");
        Visit visit = visitService.addVisit(visitDto);
        logger.info("End AddVisit");
        return visit;
    }

    @PutMapping("/visit/{id}")
    public Visit modifyVisit(@RequestBody VisitDTO visitDto, @PathVariable long id) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        logger.info("Start ModifyVisit " + id);
        Visit newVisit = visitService.modifyVisit(id, visitDto);
        logger.info("End ModifyVisit " + id);
        return newVisit;
    }

    @PatchMapping("/visit/{id}")
    public Visit patchVisit(@PathVariable long id, @RequestBody String commentary) throws VisitNotFoundException {
        logger.info("Start PatchVisit " + id);
        Visit visit = visitService.patchVisit(id, commentary);
        logger.info("End patchVisit " + id);
        return visit;
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVisitNotFoundException(VisitNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", vnfe.getMessage());
        logger.info(vnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        logger.info(pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", unfe.getMessage());
        logger.info(unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
