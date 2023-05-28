package com.coffeeandsoftware.controller;

import com.coffeeandsoftware.dto.UserDTO;
import com.coffeeandsoftware.model.User;
import com.coffeeandsoftware.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(UUID.fromString(userId)), HttpStatus.OK);
    }
}
