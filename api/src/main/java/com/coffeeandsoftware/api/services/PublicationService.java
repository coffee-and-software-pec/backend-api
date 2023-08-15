package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.PublicationUpdateDTO;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.util.PublicationWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        newPublication.set_draft(true);
        newPublication.set_private(publicationDTO.is_private());

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
        return publicationRepository.findAll().stream().filter(p -> !p.is_draft()).collect(Collectors.toList());
    }

    public List<Publication> getAllPublicationsSortedByDate() {
        return getAllPublications().stream()
                .sorted(Comparator.comparing(Publication::getCreation_date).reversed())
                .collect(Collectors.toList());

    }

    // public List<Publication> getAllPublicationsWithTag(Tag chosenTag) {
    //     return publicationRepository.findAllWithTag(chosenTag).stream().filter(pub -> !pub.is_draft()).collect(Collectors.toList());
    // }

    public int calculateTagTrend(Tag chosenTag) {
        int result = 0;
        List<TagDTO> chosenTags = new ArrayList<>(1);
        chosenTags.add(new TagDTO(chosenTag.getTitle()));
        for (Publication eachPublication : getAllPublicationsByTags(chosenTags)) {
            result += calculatePublicationScore(eachPublication);
        } return result;
    }

    public List<Publication> getAllPublicationsByTags(List<TagDTO> tags) {
        List<Publication> filtered_publications = new ArrayList<Publication>() {};
        List<Publication> all_publications = getAllPublications();

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
        } return filtered_publications;
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
            publication.set_private(publicationDTO.is_private());

            if (publicationDTO.getTagList() != null && publicationDTO.getTagList().size() > 0) {
                List<Tag> tags = tagService.createTagsIfNotExists(publicationDTO.getTagList());
                publication.setTags(tags);
            }
            publicationRepository.save(publication);
        }
        return publication;
    }

    public Publication deletePublication(String publicationId) {
        Publication publication = null;
        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();
            publicationRepository.deleteById(UUID.fromString(publicationId));
        }
        return publication;
    }

    public Publication publishPublication(String publicationId) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();
            publication.set_draft(false);
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

    public List<Publication> getAllPublicationsByPopularity() {
        List<Publication> publications = getAllPublications();
        Publication[] all_publications = publications.toArray(new Publication[publications.size()]);
        quickSortByPopularity(all_publications, 0, all_publications.length-1);
        ArrayList<Publication> result = new ArrayList<>(Arrays.asList(all_publications));
        Collections.reverse(result);
        return result;
    }

    public List<Publication> getAllPublicationsBySearch(String search) {
        List<Publication> publications = getAllPublications();
        ArrayList<Publication> result = new ArrayList<>();
        for (Publication publication : publications){
            if (publication.getTitle().contains(search) || publication.getSubtitle().contains(search) || publication.getContinuous_text().contains(search)){
                result.add(publication);
            }
        }
        return result;
    }

    public List<Publication> getAllPublicationsByTrending() {
        List<Publication> all_publications = getAllPublications();
        List<PublicationWrapper> scores = new ArrayList<>(all_publications.size());
        List<Publication> result = new ArrayList<>(all_publications.size());

        for (int i = 0; i < all_publications.size(); i++) {
            Publication pub = all_publications.get(i);
            scores.add(i, new PublicationWrapper(pub,calculatePublicationScore(pub)));
        }
        PublicationWrapper[] pws = scores.toArray(new PublicationWrapper[all_publications.size()]);
        Arrays.sort(pws, Collections.reverseOrder());
        for (int i = 0; i < all_publications.size(); i++) {
            result.add(i, pws[i].getPublication());
        }
        return result;
    }

    private static int calculatePublicationScore(Publication p) {
        int score = 0;
        LocalDateTime timeNow = LocalDateTime.now();
        if (!p.getReactions().isEmpty()) {
            for (Reaction r : p.getReactions()) {
                score += calculateReactionScore(r, timeNow);
            }
        }
         return score;
    }
    private static int calculateReactionScore(Reaction reaction, LocalDateTime timeNow) {
        LocalDateTime reaction_time = reaction.getReactionDate();
        if (reaction_time == null) {
            return 0;
        }
        if (reaction_time.until(timeNow, ChronoUnit.MONTHS) < 2) {
            if (reaction_time.until(timeNow, ChronoUnit.WEEKS) < 2) {
                if (reaction_time.until(timeNow, ChronoUnit.DAYS) < 2) {
                    if (reaction_time.until(timeNow, ChronoUnit.HOURS) < 2) {
                        return 81;
                    } return 27;
                } return 9;
            } return 3;
        } return 1;
    }

    private static void quickSortByPopularity(Publication[] collection, int begin, int end ) {
        if (begin < end) {
            int partition_index = partition(collection, begin, end);
            quickSortByPopularity(collection, begin, partition_index-1);
            quickSortByPopularity(collection, partition_index+1, end);
        }
    }

    private static int partition(Publication[] collection, int begin, int end) {
        Publication pivot = collection[end];
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (collection[j].getReactions().size() <= pivot.getReactions().size()) {
                i++;

                Publication swapTemp = collection[i];
                collection[i] = collection[j];
                collection[j] = swapTemp;
            }
        }

        Publication swapTemp = collection[i+1];
        collection[i+1] = collection[end];
        collection[end] = swapTemp;
        return i++;
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

    public List<Publication> getTrendingPublications() {
        return new ArrayList<>(getAllPublicationsByTrending());
    }

    public List<Publication> getPopularPublications() {
        return new ArrayList<>(getAllPublicationsByPopularity());
    }

    public Publication react(String publicationId, ReactionDTO reactionDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();

            List<Reaction> reactions = publication.getReactions();

            Reaction reactionToAdd = reactionService.createReactionIfPossible(publicationId, reactionDTO);
            if (reactionToAdd != null) {
                reactions.add(reactionToAdd);
                publication.setReactions(reactions);
                publicationRepository.save(publication);
            }
        }
        return publication;
    }

    public Publication unReact(String publicationId, ReactionDTO reactionDTO) {
        Publication publication = null;

        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
        if (optionalPublication.isPresent()) {
            publication = optionalPublication.get();

            List<Reaction> reactions = publication.getReactions();

            Reaction reactionToRemove = reactionService.removeReaction(publicationId, reactionDTO);
            if (reactionToRemove != null) {
                reactions.remove(reactionToRemove);
                publication.setReactions(reactions);
                publicationRepository.save(publication);
            }
        }
        return publication;
    }

    public boolean hasReacted(String publicationId, ReactionDTO reactionDTO) {
        return reactionService.hasReacted(reactionDTO.getAuthorEmail(), publicationId);
    }

    public List<Publication> getAllUserPublicationsByTags(String userId, List<TagDTO> tags) {
        List<Publication> all_publications = getAllUserPublications(userId);
        List<String> tagsTitle = tags.stream().map(TagDTO::getTitle).collect(Collectors.toList());

        if (tags.size() > 0) {
            return all_publications.stream()
                    .filter(p ->
                            new HashSet<>(p.getTags().stream()
                                    .map(Tag::getTitle)
                                    .collect(Collectors.toList()))
                            .containsAll(tagsTitle)
                    )
                    .collect(Collectors.toList());
        } else {
            return all_publications;
        }
    }
}