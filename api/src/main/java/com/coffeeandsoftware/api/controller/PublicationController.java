package com.coffeeandsoftware.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.PublicationUpdateDTO;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.dto.ReturnDTO.PublicationReturnDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.coffeeandsoftware.api.services.UserService;
import com.coffeeandsoftware.api.util.ResponseParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        var publication = publicationService.createPublication(publicationDTO);
        ResponseEntity<?> profanityFilter = checkProfanitiesRoutine(publication.getContinuous_text());
        if (profanityFilter != null) return profanityFilter;
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPublications() {
        List<Publication> publications = publicationService.getAllPublicationsSortedByDate();
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/landingPublications")
    public ResponseEntity<?> getAllLandingPublications() {
        List<Publication> publications = publicationService.getLandingPublicationsOrdered();
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/bySearch/{search}")
    public ResponseEntity<?> getAllPublicationsBySearch(@PathVariable String search) {
        List<Publication> publications = publicationService.getAllPublicationsBySearch(search);
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/trendingPublications")
    public ResponseEntity<?> getTrendingPublications() {
        List<Publication> publications = publicationService.getTrendingPublications();
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/popularPublications")
    public ResponseEntity<?> getPopularPublications() {
        List<Publication> publications = publicationService.getPopularPublications();
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new)
                        .sorted((o1, o2) -> o2.getHeartsCount() - o1.getHeartsCount())
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/userPublications/{userId}")
    public ResponseEntity<?> getAllUserPublications(@PathVariable String userId) {
        List<Publication> publications = publicationService.getAllUserPublications(userId);
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/userPublications/{userId}/byTags")
    public ResponseEntity<?> getAllUserPublicationsByTags(@PathVariable String userId, @RequestParam List<String> tags) {
        List<TagDTO> tagDTOS = tags.stream().map(TagDTO::new).collect(Collectors.toList());
        List<Publication> publications = publicationService.getAllUserPublicationsByTags(userId, tagDTOS);
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/byTag")
    public ResponseEntity<?> getAllPublicationsByTags(@RequestParam List<String> tags) {
        List<TagDTO> tagDTOS = tags.stream().map(TagDTO::new).collect(Collectors.toList());
        List<Publication> publications = publicationService.getAllPublicationsByTags(tagDTOS);
        return new ResponseEntity<>(
                publications.stream().map(PublicationReturnDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublicationById(@PathVariable String publicationId) {
        Publication publication = publicationService.getPublicationById(UUID.fromString(publicationId));
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.OK);
    }

    @PatchMapping("/{publicationId}/publish")
    @PreAuthorize("@publicationValidation.validatePublication(authentication, #publicationId)")
    public ResponseEntity<?> publishPublicationById(@PathVariable String publicationId) {
        Publication publication = publicationService.publishPublication(publicationId);
        ResponseEntity<?> profanityFilter = checkProfanitiesRoutine(publication.getContinuous_text());
        if (profanityFilter != null) return profanityFilter;
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.OK);
    }

    @PatchMapping("/{publicationId}")
    @PreAuthorize("@publicationValidation.validatePublication(authentication, #publicationId)")
    public ResponseEntity<?> updatePublicationById(@PathVariable String publicationId,
                                                   @RequestBody PublicationUpdateDTO publicationDTO) {
        Publication publication = publicationService.updatePublication(publicationId, publicationDTO);
        ResponseEntity<?> profanityFilter = checkProfanitiesRoutine(publication.getContinuous_text());
        if (profanityFilter != null) return profanityFilter;
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}")
    @PreAuthorize("@publicationValidation.validatePublication(authentication, #publicationId)")
    public ResponseEntity<?> deletePublicationById(@PathVariable String publicationId) {
        Publication publication = publicationService.deletePublication(publicationId);
        if (publication != null) {
            return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no publication with this id", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{publicationId}/react")
    @PreAuthorize("@userValidation.validateAuthenticatedUserEmail(authentication, #reactionDTO.authorEmail)")
    public ResponseEntity<?> react(@PathVariable String publicationId, @RequestBody ReactionDTO reactionDTO) {
        Publication publication = publicationService.react(publicationId, reactionDTO);
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{publicationId}/unreact")
    @PreAuthorize("@userValidation.validateAuthenticatedUserEmail(authentication, #reactionDTO.authorEmail)")
    public ResponseEntity<?> unReact(@PathVariable String publicationId, @RequestBody ReactionDTO reactionDTO) {
        Publication publication = publicationService.unReact(publicationId, reactionDTO);
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{publicationId}/hasReacted")
    public ResponseEntity<?> hasReacted(@PathVariable String publicationId, @RequestBody ReactionDTO reactionDTO) {
        boolean hasReacted = publicationService.hasReacted(publicationId, reactionDTO);
        return new ResponseEntity<>(hasReacted, HttpStatus.OK);
    }

    @PatchMapping("/{publicationId}/insertTag")
    @PreAuthorize("@publicationValidation.validatePublication(authentication, #publicationId)")
    public ResponseEntity<?> insertTagAtPublication(@PathVariable String publicationId,
                                                    @RequestBody TagDTO tagDTO) {
        Publication publication = publicationService.insertTagAtPublication(publicationId, tagDTO);
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{publicationId}/removeTag")
    @PreAuthorize("@publicationValidation.validatePublication(authentication, #publicationId)")
    public ResponseEntity<?> removeTagAtPublication(@PathVariable String publicationId,
                                                    @RequestBody TagDTO tagDTO) {
        Publication publication = publicationService.removeTagAtPublication(publicationId, tagDTO);
        return new ResponseEntity<>(new PublicationReturnDTO(publication), HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<?> checkProfanitiesRoutine(String text) {
        try {
            String response = publicationService.checkPublication(text);
            if (ResponseParser.hasProfanities(response)) {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            } return null;
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
