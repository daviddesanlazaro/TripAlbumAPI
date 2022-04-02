package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Friendship;
import com.svalero.tripalbumapi.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    List<Friendship> findAll();
    List<Friendship> findByUser(User user);
    Friendship findByUserAndFriend(User user, User friend);
}
