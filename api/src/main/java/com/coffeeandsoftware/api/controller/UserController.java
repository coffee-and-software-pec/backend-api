package com.coffeeandsoftware.api.controller;

import com.coffeeandsoftware.api.dto.ReturnDTO.UserStatsDTO;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.dto.FollowerDTO;
import com.coffeeandsoftware.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.createUser(userDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@userValidation.validateAuthenticatedUserId(authentication, #userId)")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        try {
            User user = userService.updateUser(userId, userDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(UUID.fromString(userId)), HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


    @GetMapping("/getFollowers/{userId}")
    public ResponseEntity<?> getFollowers(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getFollowers(UUID.fromString(userId)), HttpStatus.OK);
    }

    @PostMapping("/addFollower")
    public ResponseEntity<?> addFollower(@RequestBody FollowerDTO followerDTO){
        try {
            userService.addFollower(UUID.fromString(followerDTO.getId()) , UUID.fromString(followerDTO.getFollowerId()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("/removeFollower")
    public ResponseEntity<?> removeFollower(@RequestBody FollowerDTO followerDTO){
        try {
            userService.removeFollower(UUID.fromString(followerDTO.getId()) , UUID.fromString(followerDTO.getFollowerId()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    @GetMapping("/stats/{userId}/{requestUserId}")
    public ResponseEntity<?> getUserStatsById(@PathVariable String userId, @PathVariable String requestUserId) {
        UserStatsDTO userStatsDTO = userService.getUserStatsById(userId, requestUserId);
        return new ResponseEntity<>(userStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/stats/{requestUserId}")
    public ResponseEntity<?> getUserStats(@PathVariable String requestUserId) {
        List<UserStatsDTO> userStatsDTOList = userService.getUsersStats(requestUserId);
        return new ResponseEntity<>(userStatsDTOList, HttpStatus.OK);

    }
}
