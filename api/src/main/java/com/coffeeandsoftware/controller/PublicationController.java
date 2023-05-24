package com.coffeeandsoftware.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.coffeeandsoftware.model.Publication;
import com.coffeeandsoftware.repositories.PublicationRepository;
import com.coffeeandsoftware.services.UserService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
    
    @Autowired
    UserService userService;

    @Autowired
    PublicationRepository publicationRepository;

    public void savePublication(String text, LocalDateTime createdAt, String userId) {
        Publication newPublication = new Publication();
        newPublication.setContinuous_text(text);
        newPublication.setCreation_date(createdAt);
        newPublication.setAuthor(userService.getUserById(UUID.fromString(userId)));
        newPublication = publicationRepository.save(newPublication);
    }
}
