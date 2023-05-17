package com.coffeeandsoftware.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.services.UserService;

public class PublicationController {
    
    @Autowired
    UserService userService;

    @Autowired
    PublicationRepository publicationRepository;

    public void savePublication(String text, LocalDateTime createdAt, Long userId) {
        Publication newPublication = new Publication();
        newPublication.setText(text);
        newPublication.setCreatedAt(createdAt);
        newPublication.setUser(userService.getUserById(userId));
        newPublication = publicationRepository.save(newPublication);
    }
}
