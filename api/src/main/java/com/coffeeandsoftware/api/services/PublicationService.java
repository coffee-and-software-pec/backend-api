package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.PublicationUpdateDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    PublicationRepository publicationRepository;

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

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

        return filtered_publications;
//        return publicationRepository.findAllByTags(tags.stream().map(TagDTO::getTitle).collect(Collectors.toList()));
    }

    public Publication getPublicationById(String publicationId) {
        Publication publication = null;
        Optional<Publication> optionalPublication = publicationRepository.findById(UUID.fromString(publicationId));
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
            publicationRepository.save(publication);
        }

        return publication;
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
}
