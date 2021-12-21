package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.VisitNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    // Mostrar las visitas realizadas por un usuario determinado a un lugar determinado
    List<Visit> findByUserAndPlace(User user, Place place);

    // Mostrar visitas a un lugar con mejor valoración que la determinada
    @Query ("SELECT v FROM Visit v WHERE place = :place AND rating >= :rating")
    List<Visit> findByPlaceRating(@Param("place") Place place, @Param("rating") float rating);

    // Eliminar todas las visitas de un usuario con menor valoración que la determinada
    @Transactional
    @Modifying
    @Query("DELETE FROM Visit WHERE user = :user AND rating <= :rating")
    void deleteByUserRating(@Param("user") User user, @Param("rating") float rating);

}
