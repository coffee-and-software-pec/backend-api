package com.coffeeandsoftware.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeeandsoftware.dto.TagDTO;
import com.coffeeandsoftware.model.Tag;
import com.coffeeandsoftware.repositories.TagRepository;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepo;

    public Tag createTag(TagDTO tagDTO) {
        Tag t = new Tag();
        t.setTitle(tagDTO.getTitle());

        return tagRepo.save(t);
    }

    public Tag getTagById(UUID id) {
        Tag t = null;
        Optional<Tag> optionalTag = tagRepo.findById(id);

        if (optionalTag.isPresent()) {
            t = optionalTag.get();
        }

        return t;
    }

    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }
    
}
