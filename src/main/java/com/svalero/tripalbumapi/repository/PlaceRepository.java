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

    @Query(value = "SELECT AVG(\"rating\") FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    float averageRating(long placeId);

    @Query(value = "SELECT COUNT(*) FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    int numVisits(long placeId);

    @Query(value = "SELECT COUNT(DISTINCT \"user_id\") FROM \"visits\" WHERE \"place_id\" = ?1", nativeQuery = true)
    int numUsers(long placeId);
}
