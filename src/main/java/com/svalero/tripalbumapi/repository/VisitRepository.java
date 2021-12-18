package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.VisitNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long>  {
    List<Visit> findAll();
    List<Visit> findByUser(User user);
    List<Visit> findByPlace(Place place);

    // Mostrar las visitas realizadas después de una fecha determinada
    @Query("SELECT v FROM Visit v WHERE date >= :date")
    List<Visit> findRecentVisits(@Param("date") LocalDate localDate);

    // Mostrar el comentario realizado sobre una visita
    @Query("SELECT commentary FROM Visit v WHERE id = :id")
    String findCommentary(@Param("id") long id) throws VisitNotFoundException;
}
