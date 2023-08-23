package com.coffeeandsoftware.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.coffeeandsoftware.api.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeeandsoftware.api.model.Revision;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.RevisionRepository;

@Service
public class RevisionService {
    @Autowired
    RevisionRepository revisionRepository;

    public Revision createRevision(String text, User author, String comment, Publication publication){
        Revision revision = new Revision(text, author, comment, LocalDateTime.now(), publication);
        return revisionRepository.save(revision);
    }

    public List<Revision> getRevisionByPublication(Publication publication) {
        return revisionRepository.findAllByPublication(publication);
    }

    public void deleteReview(String reviewId) {
        Optional<Revision> revision = revisionRepository.findById(UUID.fromString(reviewId));
        revision.ifPresent(value -> revisionRepository.delete(value));
    }

    public Revision updateReview(String reviewId, String reviewText) {
        Optional<Revision> revision = revisionRepository.findById(UUID.fromString(reviewId));
        Revision revisionResult = null;
        if (revision.isPresent()) {
            revisionResult = revision.get();
            revisionResult.setComment(reviewText);
            revisionRepository.save(revisionResult);
        }

        return revisionResult;
    }
}
