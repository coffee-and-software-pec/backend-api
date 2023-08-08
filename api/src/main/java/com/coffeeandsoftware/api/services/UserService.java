package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.ReturnDTO.UserStatsDTO;
import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PublicationRepository publicationRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setU_name(userDTO.getName());
        user.setPhotoURL(userDTO.getPhotoURL());

        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent()) user = u.get();
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Set<UUID> getFollowers(UUID id) {
        Set<UUID> followers = null; 
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) followers = optionalUser.get().getFollowers();
        return followers;
    }

    public void addFollower(UUID id, UUID followerId) throws Exception {
        User user = null; 
        Optional<User> u = userRepository.findById(id);
        if (!u.isPresent()) throw new Exception();
        else {
            Optional<User> follower = userRepository.findById(followerId);
            if (!u.isPresent()) throw new Exception();
            else {
                Set<UUID> followers = u.get().getFollowers();
                followers.add(followerId);
                u.get().setFollowers(followers);
                userRepository.save(u.get());

            }
        }
    }
    
    public UserStatsDTO getUserStatsById(String userId, String requestUserId) {
        User user = getUserById(UUID.fromString(userId));
        return mapUserToUserStats(user);
    }

    public List<UserStatsDTO> getUsersStats(String requestUserId) {
        List<UserStatsDTO> userStatsDTOList = new ArrayList<>();

        List<User> users = getAllUsers();

        return users.stream().map(this::mapUserToUserStats).collect(Collectors.toList());
    }

    private UserStatsDTO mapUserToUserStats(User user) {
        UserStatsDTO userStatsDTO = new UserStatsDTO();
        userStatsDTO.setId(user.getU_id().toString());
        userStatsDTO.setName(user.getU_name());
        userStatsDTO.setEmail(user.getEmail());
        userStatsDTO.setPhotoURL(user.getPhotoURL());
        userStatsDTO.setBio("");
        userStatsDTO.setFollowingCount(0);
        userStatsDTO.setFollowersCount(0);
        List<Publication> publications = publicationRepository.findAllByAuthor(user);
        userStatsDTO.setPosts(publications.size());
        userStatsDTO.setLikes(publications.stream().map(post -> post.getReactions().size()).mapToInt(Integer::intValue).sum());
        userStatsDTO.setComments(publications.stream().map(post -> post.getComments().size()).mapToInt(Integer::intValue).sum());
        // implement here the check of the followers list
        // requestUser.followList.any(userId)
        userStatsDTO.setFollowing(false);
        return userStatsDTO;
    }
}
