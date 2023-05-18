package com.coffeeandsoftware.api.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.services.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
    
    @Autowired
    UserService userService;

    @Autowired
    PublicationRepository publicationRepository;

    public void savePublication(String text, LocalDateTime createdAt, String userId) {
        Publication newPublication = new Publication();
        newPublication.setText(text);
        newPublication.setCreatedAt(createdAt);
        newPublication.setUser(userService.getUserById(UUID.fromString(userId)));
        newPublication = publicationRepository.save(newPublication);
    }
}
