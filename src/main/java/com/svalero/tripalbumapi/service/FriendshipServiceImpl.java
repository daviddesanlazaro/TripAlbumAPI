package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Friendship;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.dto.FriendshipDTO;
import com.svalero.tripalbumapi.exception.FriendshipNotFoundException;
import com.svalero.tripalbumapi.exception.UserNotFoundException;
import com.svalero.tripalbumapi.repository.FriendshipRepository;
import com.svalero.tripalbumapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Friendship> findAllFriendships() {
        return friendshipRepository.findAll();
    }

    @Override
    public List<Friendship> findByUser(User user) {
        return friendshipRepository.findByUser(user);
    }

    @Override
    public Friendship findByUserAndFriend(User user, User friend) {
        return friendshipRepository.findByUserAndFriend(user, friend);
    }

    @Override
    public Friendship findFriendship(long id) throws FriendshipNotFoundException {
        return friendshipRepository.findById(id)
                .orElseThrow(FriendshipNotFoundException::new);
    }

    @Override
    public Friendship addFriendship(FriendshipDTO friendshipDTO) throws UserNotFoundException {
        User user = userRepository.findById(friendshipDTO.getUser())
                .orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendshipDTO.getFriend())
                .orElseThrow(UserNotFoundException::new);

        Friendship friendship = new Friendship(0, user, friend);
        return friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriendship(long id) throws FriendshipNotFoundException {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(FriendshipNotFoundException::new);
        friendshipRepository.delete(friendship);
    }
}
