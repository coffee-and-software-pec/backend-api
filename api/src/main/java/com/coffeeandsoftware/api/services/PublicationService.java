package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.PublicationUpdateDTO;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.dto.ReturnDTO.PublicationReturnDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    PublicationRepository publicationRepository;

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    ReactionService reactionService;

    public Publication createPublication(PublicationDTO publicationDTO) {
        User author = userService.getUserById(UUID.fromString(publicationDTO.getAuthor_id()));

        Publication newPublication = new Publication();
        newPublication.setTitle(publicationDTO.getTitle());
        newPublication.setSubtitle(publicationDTO.getSubtitle());
        newPublication.setContinuous_text(publicationDTO.getContinuous_text());
        newPublication.setMain_img_url(publicationDTO.getMain_img_url());

        LocalDateTime timeNow = LocalDateTime.now();

        newPublication.setCreation_date(timeNow);
        newPublication.setLast_modification(timeNow);
        newPublication.setAuthor(author);

        if (publicationDTO.getTagList() != null && publicationDTO.getTagList().size() > 0) {
            List<Tag> tags = tagService.createTagsIfNotExists(publicationDTO.getTagList());
            newPublication.setTags(tags);
        }

        publicationRepository.save(newPublication);

        return newPublication;
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public List<Publication> getAllPublicationsByTags(List<TagDTO> tags) {
        List<Publication> filtered_publications = new ArrayList<Publication>() {};
        List<Publication> all_publications = publicationRepository.findAll();

        if (tags.size() > 0) {
            for (Publication publication:all_publications){
                Boolean have_tag = false;
                for (Tag tag:publication.getTags()){
                    for (TagDTO wanted_tag:tags){
                        if (tag.getTitle().equals(wanted_tag.getTitle())) {
                            have_tag = true;
                            break;
                        }
                    }
                }
                if (have_tag) filtered_publications.add(publication);
            }
        } else {
            filtered_publications = all_publications;
        }

        return filtered_publications;
//        return publicationRepository.findAllByTags(tags.stream().map(TagDTO::getTitle).collect(Collectors.toList()));
    }

    public Publication getPublicationById(UUID publicationId) {
        Publication publication = null;
        Optional<Publication> optionalPublication = publicationRepository.findById(publicationId);
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();
        }
        return publication;
    }

    public Publication updatePublication(String publicationId, PublicationUpdateDTO publicationDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();
            publication.setTitle(publicationDTO.getTitle());
            publication.setSubtitle(publicationDTO.getSubtitle());
            publication.setContinuous_text(publicationDTO.getContinuous_text());
            publication.setMain_img_url(publicationDTO.getMain_img_url());
            publication.setLast_modification(LocalDateTime.now());

            if (publicationDTO.getTagList() != null && publicationDTO.getTagList().size() > 0) {
                List<Tag> tags = tagService.createTagsIfNotExists(publicationDTO.getTagList());
                publication.setTags(tags);
            }
            publicationRepository.save(publication);
        }
        return publication;
    }

    public boolean hasReacted(String publicationId, String userEmail) {
        List<Reaction> all_reactions = reactionService.getAllReactions(publicationId);

        for (Reaction r : all_reactions) {
            if (r.getAuthor().getEmail().equals(userEmail)) {
                return true;
            }
        } return false;
    }

    public Publication insertTagAtPublication(String publicationId, TagDTO tagDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();

            List<Tag> tags = publication.getTags();

            Tag tagToAdd = tagService.createTagIfNotExists(tagDTO);
            if (!tags.contains(tagToAdd)) {
                tags.add(tagToAdd);
            }
            publication.setTags(tags);
            publicationRepository.save(publication);
        }

        return publication;
    }

    public Publication removeTagAtPublication(String publicationId, TagDTO tagDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();

            List<Tag> tags = publication.getTags();

            Tag tagToAdd = tagService.createTagIfNotExists(tagDTO);
            tags.remove(tagToAdd);
            publication.setTags(tags);
            publicationRepository.save(publication);
        }

        return publication;
    }

    public List<Publication> getAllPublicationsOrdered() {
        List<Publication> all_publications = getAllPublications();
        Collections.sort(all_publications);
        return all_publications;
    }

    public List<Publication> getAllUserPublications(String userId) {
        User user = userService.getUserById(UUID.fromString(userId));
        return publicationRepository.findAllByAuthor(user);
    }

    public List<Publication> getLandingPublicationsOrdered() {
        return getAllPublications().stream()
                .sorted(Comparator.comparing(Publication::getCreation_date).reversed())
                .limit(4)
                .collect(Collectors.toList());
    }

    public Publication react(String publicationId, String userEmail, ReactionDTO reactionDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();

            List<Reaction> reactions = publication.getReactions();

            Reaction reactionToAdd = reactionService.createReactionIfPossible(reactionDTO);
            reactions.add(reactionToAdd);
            publication.setReactions(reactions);
            publicationRepository.save(publication);
        }
        return publication;
    }
}