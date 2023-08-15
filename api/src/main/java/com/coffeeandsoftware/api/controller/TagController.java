package com.coffeeandsoftware.api.controller;

import com.coffeeandsoftware.api.dto.ReturnDTO.TagReturnDTO;
import com.coffeeandsoftware.api.dto.TagDTO;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagReturnDTO> tagReturnDTOS = tags.stream().map(t -> new TagReturnDTO(t.getTitle()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagReturnDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDTO tagDTO) {
        Tag tag = tagService.createTag(tagDTO);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping("/trendingTags")
    public ResponseEntity<?> getTrendingTags() {
        List<Tag> tags = tagService.getAllTagsByTrending();
        List<TagReturnDTO> tagReturnDTOS = tags.stream().map(t -> new TagReturnDTO(t.getTitle()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagReturnDTOS, HttpStatus.OK);
    }
}
