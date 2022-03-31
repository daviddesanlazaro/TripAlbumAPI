package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Friendship;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.dto.FriendshipDTO;
import com.svalero.tripalbumapi.exception.FriendshipNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;

import java.util.List;

public interface FriendshipService {
    List<Friendship> findAllFriendships();
    List<Friendship> findFriendships(User user);
    Friendship findFriendship(long id) throws FriendshipNotFoundException;
    Friendship addFriendship(FriendshipDTO friendDto) throws UserNotFoundException;
    void deleteFriendship(long id) throws FriendshipNotFoundException;
}
