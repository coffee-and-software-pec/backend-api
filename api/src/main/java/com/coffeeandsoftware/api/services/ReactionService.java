package com.coffeeandsoftware.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.PublicationRepository;

@RestController
@RequestMapping("/reaction")
@Service
public class ReactionService {

    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

    @Autowired
    PublicationRepository publicationRepository;

    public Reaction createReaction(ReactionDTO reactionDTO) {
        User author = userService.getUserById(reactionDTO.getAuthor_id());
        Publication publication = publicationService.getPublicationById(reactionDTO.getPublication_id().toString());

        Reaction newReaction = new Reaction();
        newReaction.setAuthor(author);
        newReaction.setR_publication(publication);
        newReaction.setR_type(reactionDTO.getType());
        return newReaction;
    }
}
