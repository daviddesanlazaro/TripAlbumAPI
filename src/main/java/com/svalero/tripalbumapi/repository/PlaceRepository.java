package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findAll();
    List<Place> findByProvince(Province province);

    // Mostrar la valoración media de un lugar
    @Query(value = "SELECT AVG(\"rating\") FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    float averageRating(long placeId);

    // Contar las visitas totales de un lugar
    @Query(value = "SELECT COUNT(*) FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    int numVisits(long placeId);

    // Contar los usuarios únicos que han visitado un lugar
    @Query(value = "SELECT COUNT(DISTINCT \"user_id\") FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    int numUsers(long placeId);

    // Mostrar lugares de una provincia con mayor latitud que la determinada
    @Query("SELECT p FROM Place p WHERE province = :province AND latitude >= :latitude")
    List<Place> findByProvinceLatitude(@Param("province") Province province, @Param("latitude") float latitude);
}
