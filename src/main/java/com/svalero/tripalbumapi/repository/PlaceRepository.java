package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

    // Búsqueda por nombre
    List<Place> findByNameContains(String name);

    // Búsqueda por nombre y provincia
    List<Place> findByProvinceAndNameContains(Province province, String name);
}
