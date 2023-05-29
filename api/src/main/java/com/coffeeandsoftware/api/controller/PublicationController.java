package com.coffeeandsoftware.api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.PublicationUpdateDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.coffeeandsoftware.api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publication")
public class PublicationController {
    
    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

    @PostMapping
    public ResponseEntity<?> createPublication(@RequestBody PublicationDTO publicationDTO) {
        Publication publication = publicationService.createPublication(publicationDTO);
        return new ResponseEntity<>(publication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPublications() {
        List<Publication> publications = publicationService.getAllPublications();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/byTag")
    public ResponseEntity<?> getAllPublicationsByTags(@RequestBody List<TagDTO> tags) {
        List<Publication> publications = publicationService.getAllPublicationsByTags(tags);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublicationById(@PathVariable String publicationId) {
        Publication publication = publicationService.getPublicationById(publicationId);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PatchMapping("/{publicationId}")
    public ResponseEntity<?> updatePublicationById(@PathVariable String publicationId,
                                                   @RequestBody PublicationUpdateDTO publicationDTO) {
        Publication publication = publicationService.updatePublication(publicationId, publicationDTO);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PatchMapping("/{publicationId}/insertTag")
    public ResponseEntity<?> insertTagAtPublication(@PathVariable String publicationId,
                                                    @RequestBody TagDTO tagDTO) {
        Publication publication = publicationService.insertTagAtPublication(publicationId, tagDTO);
        return new ResponseEntity<>(publication, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{publicationId}/removeTag")
    public ResponseEntity<?> removeTagAtPublication(@PathVariable String publicationId,
                                                    @RequestBody TagDTO tagDTO) {
        Publication publication = publicationService.removeTagAtPublication(publicationId, tagDTO);
        return new ResponseEntity<>(publication, HttpStatus.NO_CONTENT);
    }


}
