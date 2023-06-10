package com.coffeeandsoftware.api.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coffeeandsoftware.api.dto.CommentDTO;
import com.coffeeandsoftware.api.dto.CommentUpdateDTO;
import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.services.CommentService;
import com.coffeeandsoftware.api.services.PublicationService;
import com.coffeeandsoftware.api.services.UserService;
import com.coffeeandsoftware.api.model.Comment;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
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
