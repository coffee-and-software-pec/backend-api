package com.coffeeandsoftware.api.services;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.repositories.TagRepository;
import com.coffeeandsoftware.api.util.TagWrapper;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepo;

    @Autowired
    PublicationService publicationService;

    public Tag createTag(TagDTO tagDTO) {
        Tag t = new Tag();
        t.setTitle(tagDTO.getTitle());

        return tagRepo.save(t);
    }

    public Tag createTagIfNotExists(TagDTO tagDTO) {
        Optional<Tag> optionalTag = getTagByTitle(tagDTO.getTitle());
        return optionalTag.orElseGet(() -> createTag(tagDTO));
    }

    public List<Tag> createTagsIfNotExists(List<TagDTO> tags) {
        List<Tag> resultList = new ArrayList<>();

        for (TagDTO tagDTO: tags) {
            Optional<Tag> optionalTag = getTagByTitle(tagDTO.getTitle());
            optionalTag.ifPresentOrElse(
                    resultList::add,
                    () -> resultList.add(createTag(tagDTO))
            );
        }

        return resultList;
    }

    public Tag getTagById(UUID id) {
        Tag t = null;
        Optional<Tag> optionalTag = tagRepo.findById(id);

        if (optionalTag.isPresent()) {
            t = optionalTag.get();
        }
        return t;
    }

    public Optional<Tag> getTagByTitle(String title) {
        return tagRepo.findByTitle(title);
    }

    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    public List<Tag> getAllTagsByTrending() {
        List<Tag> tagList = getAllTags();
        ArrayList<TagWrapper> scoredTags = new ArrayList<>(tagList.size());
        for (Tag eachTag : tagList) {
            scoredTags.add(new TagWrapper(eachTag, publicationService.calculateTagTrend(eachTag)));
        }

        Collections.sort(scoredTags);
        ArrayList<Tag> trendingTags = new ArrayList<>(scoredTags.size());
        for (int index = 0; index < scoredTags.size(); index++) {
            trendingTags.add(scoredTags.get(index).getTag());
        }
        return trendingTags;
    }
}
