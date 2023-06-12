package com.coffeeandsoftware.api.controller;

import java.util.List;
import java.util.UUID;

import com.coffeeandsoftware.api.dto.ReturnDTO.CommentReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(commentDTO);
        return new ResponseEntity<>(new CommentReturnDTO(comment), HttpStatus.CREATED);
    }

    @GetMapping("/byPublication/{publicationId}")
    public ResponseEntity<?> getAllComments(@PathVariable String publicationId) {
        List<Comment> comments = commentService.getAllCommentsByPublication(publicationId);
        return new ResponseEntity<>(comments.stream().map(CommentReturnDTO::new), HttpStatus.OK);
    }

    @GetMapping("/byPublication")
    public ResponseEntity<?> getAllComments(@RequestBody PublicationDTO publicationDTO) {
        List<Comment> comments = commentService.getAllComments(publicationDTO);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<?> getAllCommentsByAuthor(@RequestBody UserDTO author) {
        List<Comment> comments = commentService.getAllCommentsByAuthor(author);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable String commentId) {
        Comment comment = commentService.getCommentById(UUID.fromString(commentId));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateCommentById(@PathVariable String commentId,
                                                @RequestBody CommentUpdateDTO commentUpdateDTO) {
        Comment comment = commentService.updateComment(UUID.fromString(commentId), commentUpdateDTO);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
