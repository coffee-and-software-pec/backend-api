package com.coffeeandsoftware.api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


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


    public List<Publication> getPublicationsbyTags(List<String> tags){
        List<Publication> filtered_publications = new ArrayList<Publication>() {};
        List<Publication> all_publications = publicationRepository.findAll();

        for (Publication publication:all_publications){
            Boolean have_tag = false;
            for (String tag:publication.getTags()){
                for (String wanted_tag:tags){
                    if (tag.equals(wanted_tag)){
                        have_tag = true;
                    }
                }
            }
            if (have_tag) filtered_publications.add(publication);
        }

        return filtered_publications;
    }

}
