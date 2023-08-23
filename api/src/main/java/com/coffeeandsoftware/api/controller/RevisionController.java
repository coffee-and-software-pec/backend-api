package com.coffeeandsoftware.api.controller;

import com.coffeeandsoftware.api.dto.ReturnDTO.PublicationReturnDTO;
import com.coffeeandsoftware.api.dto.ReturnDTO.ReviewReturnDTO;
import com.coffeeandsoftware.api.dto.ReviewDTO;
import com.coffeeandsoftware.api.dto.ReviewUpdateDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Revision;
import com.coffeeandsoftware.api.services.PublicationService;
import com.coffeeandsoftware.api.services.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
public class RevisionController {

    @Autowired
    PublicationService publicationService;

    @Autowired
    RevisionService revisionService;

    @PostMapping(("/{publicationId}"))
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO, @PathVariable String publicationId) {
        Publication publication = publicationService.createRevision(reviewDTO.getText(), reviewDTO.getAuthorId(),
                reviewDTO.getComment(), publicationId);
        PublicationReturnDTO publicationDTO = new PublicationReturnDTO(publication);
        return new ResponseEntity<>(publicationDTO, HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getReviews(@PathVariable String publicationId) {
        List<Revision> revisionList = publicationService.getAllReviewsByPublication(publicationId);

        List<ReviewReturnDTO> reviewReturnDTOS = revisionList.stream().map(ReviewReturnDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviewReturnDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    @PreAuthorize("@reviewValidation.validateReview(authentication, #reviewId)")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateDTO reviewUpdateDTO, @PathVariable String reviewId) {
        Revision revision = revisionService.updateReview(reviewId, reviewUpdateDTO.getReviewText());
        return new ResponseEntity<>(new ReviewReturnDTO(revision), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("@reviewValidation.validateReview(authentication, #reviewId)")
    public ResponseEntity<?> deleteReview(@PathVariable String reviewId) {
        revisionService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
