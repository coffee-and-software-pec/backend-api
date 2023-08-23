package com.coffeeandsoftware.api.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.coffeeandsoftware.api.dto.ReturnDTO.CommentReturnDTO;
import com.coffeeandsoftware.api.util.CheckProfanity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.coffeeandsoftware.api.dto.CommentDTO;
import com.coffeeandsoftware.api.dto.CommentUpdateDTO;
import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.services.CommentService;
import com.coffeeandsoftware.api.services.PublicationService;
import com.coffeeandsoftware.api.services.UserService;
import com.coffeeandsoftware.api.model.Comment;
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

    @Autowired
    CommentService commentService;

    @Autowired
    CheckProfanity checkProfanity;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        ResponseEntity<?> profanityFilter = checkProfanity.checkProfanitiesRoutine(commentDTO.getC_text());
        if (profanityFilter != null) return profanityFilter;
        Comment comment = commentService.createComment(commentDTO);
        return new ResponseEntity<>(new CommentReturnDTO(comment), HttpStatus.CREATED);
    }

    @GetMapping("/byPublication/{publicationId}")
    public ResponseEntity<?> getAllComments(@PathVariable String publicationId) {
        List<Comment> comments = commentService.getAllCommentsByPublication(publicationId);

        HashMap<UUID, List<Comment>> repliesMap = new HashMap<>();
        for (Comment comment: comments) {
            if (comment.getParent() == null) {
                repliesMap.put(comment.getC_id(), new ArrayList<>());
            } else {
                UUID parentId = comment.getParent().getC_id();
                if (repliesMap.get(parentId) == null) {
                    repliesMap.put(parentId, Collections.singletonList(comment));
                } else {
                    repliesMap.get(parentId).add(comment);
                }
            }
        }

        List<CommentReturnDTO> commentReturnDTOS = comments.stream()
                .filter(c -> c.getParent() == null)
                .map(c -> new CommentReturnDTO(c, repliesMap.get(c.getC_id()))).collect(Collectors.toList());
        return new ResponseEntity<>(commentReturnDTOS, HttpStatus.OK);
    }

    @GetMapping("/byPublication")
    public ResponseEntity<?> getAllComments(@RequestBody PublicationDTO publicationDTO) {
        List<Comment> comments = commentService.getAllComments(publicationDTO);
        return new ResponseEntity<>(comments.stream().map(CommentReturnDTO::new), HttpStatus.OK);
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<?> getAllCommentsByAuthor(@RequestBody UserDTO author) {
        List<Comment> comments = commentService.getAllCommentsByAuthor(author);
        return new ResponseEntity<>(comments.stream().map(CommentReturnDTO::new), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable String commentId) {
        Comment comment = commentService.getCommentById(UUID.fromString(commentId));
        return new ResponseEntity<>(new CommentReturnDTO(comment), HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("@commentValidation.validateComment(authentication, #commentId)")
    public ResponseEntity<?> updateCommentById(@PathVariable String commentId,
                                                @RequestBody CommentUpdateDTO commentUpdateDTO) {
        ResponseEntity<?> profanityFilter = checkProfanity.checkProfanitiesRoutine(commentUpdateDTO.getText());
        if (profanityFilter != null) return profanityFilter;
        Comment comment = commentService.updateComment(UUID.fromString(commentId), commentUpdateDTO);
        return new ResponseEntity<>(new CommentReturnDTO(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("@commentValidation.validateComment(authentication, #commentId)")
    public ResponseEntity<?> deleteCommentById(@PathVariable String commentId) {
        Comment comment = commentService.deleteComment(UUID.fromString(commentId));
        if (comment != null) {
            return new ResponseEntity<>(new CommentReturnDTO(comment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no comment with this id", HttpStatus.NOT_FOUND);
        }
    }
}
