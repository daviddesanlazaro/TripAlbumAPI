package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VisitController {
    @Autowired
    private VisitService visitService;

    @GetMapping("/visits")
    public List<Visit> getVisits() {
        List<Visit> visits;

        visits = visitService.findAllVisits();
        return visits;
    }

    @GetMapping("/visit/{id}")
    public Visit getVisit(@PathVariable long id) throws VisitNotFoundException {
        Visit visit = visitService.findVisit(id);
        return visit;
    }

    @DeleteMapping("/visit/{id}")
    public Visit removeVisit(@PathVariable long id) throws VisitNotFoundException {
        Visit visit = visitService.deleteVisit(id);
        return visit;
    }

    @PostMapping("/visits")
    public Visit addVisit(@RequestBody VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        Visit visit = visitService.addVisit(visitDto);
        return visit;
    }

    @PutMapping("/visit/{id}")
    public Visit modifyVisit(@RequestBody VisitDTO visitDto, @PathVariable long id) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        Visit newVisit = visitService.modifyVisit(id, visitDto);
        return newVisit;
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVisitNotFoundException(VisitNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", vnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
