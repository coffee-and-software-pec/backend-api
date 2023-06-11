package com.coffeeandsoftware.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.coffeeandsoftware.api.util.ReactionPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.ReactionRepository;

@Service
public class ReactionService {

    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

    @Autowired
    ReactionRepository reactionRepository;

    public Reaction createReaction(String publicationId, ReactionDTO reactionDTO) {
        User author = userService.getUserByEmail(reactionDTO.getAuthorEmail());
        Publication publication = publicationService.getPublicationById(UUID.fromString(publicationId));

        Reaction newReaction = new Reaction();
        newReaction.setAuthor(author);
        newReaction.setR_publication(publication);
        newReaction.setR_type(reactionDTO.getType());
        return newReaction;
    }

    public List<Reaction> getAllReactions(String publicationId) {
        Publication pub = publicationService.getPublicationById(UUID.fromString(publicationId));
        return pub.getReactions();
    }

    public List<Reaction> getAllReactionsByType(String publicationId, String type) {
        List<Reaction> filtered_reactions = new ArrayList<Reaction>();
        List<Reaction> all_reactions = getAllReactions(publicationId);

        for (Reaction r : all_reactions) {
            Boolean matchesType = false;
            if (r.getR_type().matches(type)) {
                matchesType = true;
                break;
            }
            if (matchesType) filtered_reactions.add(r);
        } return filtered_reactions;
    }

    public boolean hasReacted(String userEmail, String publicationId) {
        List<Reaction> all_reactions = getAllReactions(publicationId);

        for (Reaction r : all_reactions) {
            if (r.getAuthor().getEmail().equals(userEmail)) {
                return true;
            }
        } return false;
    }

    /*
     * TODO UNSAFE this method does not check if the reaction was actually removed.
     */
    public Reaction removeReaction(String publicationId, ReactionDTO reactionDTO) {
        Reaction reaction = null;

        User u = userService.getUserByEmail(reactionDTO.getAuthorEmail());
        Optional<Reaction> optionalReaction = reactionRepository.findById(new ReactionPK(u.getU_id(), UUID.fromString(publicationId)));
        if (optionalReaction.isPresent()) {
            reaction = optionalReaction.get();
            reactionRepository.delete(reaction);
        }
        return reaction;
    }

    public Reaction createReactionIfPossible(String publicationId, ReactionDTO reactionDTO) {
        if (!hasReacted(reactionDTO.getAuthorEmail(), publicationId)) {
            Publication publication = publicationService.getPublicationById(UUID.fromString(publicationId));
            User user = userService.getUserByEmail(reactionDTO.getAuthorEmail());

            Reaction reaction = new Reaction();
            reaction.setAuthor(user);
            reaction.setR_publication(publication);
            reaction.setR_type(reactionDTO.getType());
            return reactionRepository.save(reaction);
        } return null;

    }
}
