package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.domain.dto.VisitDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.VisitService;
import lombok.Builder;
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
public class VisitController {

    private final Logger logger = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private VisitService visitService;

    // Mostrar todas las visitas
    @GetMapping("/visits")
    public ResponseEntity<?> getVisits() {
        logger.info("Start getVisits");
        List<Visit> visits;

        visits = visitService.findAllVisits();
        logger.info("End getVisits");
        return ResponseEntity.ok(visits);
    }

    // Mostrar una visita por ID
    @GetMapping("/visit/{id}")
    public ResponseEntity<?> getVisit(@PathVariable long id) throws VisitNotFoundException {
        logger.info("Start ShowVisit " + id);
        Visit visit = visitService.findVisit(id);
        logger.info("End ShowVisit " + id);
        return ResponseEntity.ok(visit);
    }

    // Eliminar una visita
    @DeleteMapping("/visit/{id}")
    public void removeVisit(@PathVariable long id) throws VisitNotFoundException {
        logger.info("Start DeleteVisit " + id);
        visitService.deleteVisit(id);
        logger.info("End DeleteVisit " + id);
    }

    // Insertar una visita
    @PostMapping("/visits")
    public ResponseEntity<?> addVisit(@Valid @RequestBody VisitDTO visitDto) throws UserNotFoundException, PlaceNotFoundException {
        logger.info("Start AddVisit");

//        if (((visitDto.getDate() == null) || (visitDto.getUser() == 0)) || (visitDto.getPlace() == 0)) {
//            String error = "Los siguientes campos son incorrectos:";
//            if (visitDto.getDate() == null)
//                error = error + " Fecha";
//            if (visitDto.getUser() == 0)
//                error = error + " Usuario";
//            if (visitDto.getPlace() == 0)
//                error = error + " Lugar";
//            return ResponseEntity.badRequest().body(ErrorResponse.badRequest(error));
//        }
//        logger.info("Request accepted");

        Visit visit = visitService.addVisit(visitDto);
        logger.info("End AddVisit");
        return ResponseEntity.ok(visit);
    }

    // Modificar una visita
    @PutMapping("/visit/{id}")
    public ResponseEntity<?> modifyVisit(@Valid @RequestBody VisitDTO visitDto, @PathVariable long id) throws VisitNotFoundException, UserNotFoundException, PlaceNotFoundException {
        logger.info("Start ModifyVisit " + id);
        if (visitDto.getCommentary() == null)
            visitDto.setCommentary("");

//        if (((visitDto.getDate() == null) || (visitDto.getUser() == 0)) || (visitDto.getPlace() == 0)) {
//            String error = "Los siguientes campos son incorrectos:";
//            if (visitDto.getDate() == null)
//                error = error + " Fecha";
//            if (visitDto.getUser() == 0)
//                error = error + " Usuario";
//            if (visitDto.getPlace() == 0)
//                error = error + " Lugar";
//            return ResponseEntity.badRequest().body(ErrorResponse.badRequest(error));
//        }
//        logger.info("Request accepted");

        Visit newVisit = visitService.modifyVisit(id, visitDto);
        logger.info("End ModifyVisit " + id);
        return ResponseEntity.ok(newVisit);
    }

    // Cambiar el comentario de una visita. JPQL
    @PatchMapping("/visit/{id}")
    public ResponseEntity<?> patchVisit(@PathVariable long id, @RequestBody String commentary) throws VisitNotFoundException {
        logger.info("Start PatchVisit " + id);

//        if (commentary == null)
//            return ResponseEntity.badRequest().body(ErrorResponse.badRequest("El comentario no es correcto"));
//        logger.info("Request accepted");

        Visit visit = visitService.patchVisit(id, commentary);
        logger.info("End patchVisit " + id);
        return ResponseEntity.ok(visit);
    }

//    // Mostrar las visitas realizadas después de una fecha determinada. JPQL
//    @GetMapping("/visits/date")
//    public List<Visit> findRecentVisits(@RequestBody String date) {
//        logger.info("Start findRecentVisits. Convert date");
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate localDate = LocalDate.parse(date, dtf);
//        logger.info("Date converted");
//        List<Visit> visits = visitService.findRecentVisits(localDate);
//        logger.info("End findRecentVisits");
//        return visits;
//    }

//    // Mostrar el comentario realizado sobre una visita. JPQL
//    @GetMapping("/visit/{id}/commentary")
//    public ResponseEntity<?> findCommentary(@PathVariable long id) throws VisitNotFoundException {
//        logger.info("Start findCommentary");
//        String commentary = visitService.findCommentary(id);
//        logger.info("End findCommentary");
//        return ResponseEntity.ok(commentary);
//    }

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

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVisitNotFoundException(VisitNotFoundException vnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(vnfe.getMessage()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFoundException(PlaceNotFoundException pnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(unfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
