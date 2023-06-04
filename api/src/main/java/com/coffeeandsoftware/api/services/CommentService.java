package com.coffeeandsoftware.api.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coffeeandsoftware.api.dto.CommentDTO;
import com.coffeeandsoftware.api.dto.CommentUpdateDTO;
import com.coffeeandsoftware.api.dto.PublicationDTO;
import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.CommentRepository;

@RestController
@RequestMapping("/comment")
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    PublicationService pubService;

    @Autowired
    CommentService commentService;

    public Comment createComment(CommentDTO commentDTO) {
        User author = userService.getUserById(commentDTO.getAuthor_id());
        Publication pub = pubService.getPublicationById(commentDTO.getPublication_id().toString());
        Comment parent = commentService.getCommentById(commentDTO.getC_id());
        LocalDateTime timeNow = LocalDateTime.now();

        Comment newComment = new Comment();
        newComment.setPublication(pub);
        newComment.setAuthor(author);
        newComment.setC_text(commentDTO.getC_text());
        newComment.setParent(parent);
        newComment.setCreation_date(timeNow);

        commentRepository.save(newComment);
        return newComment;
    }

    public List<Comment> getAllComments(PublicationDTO publicationDTO) {
        List<Comment> filtered_comments = new ArrayList<Comment>() {};
        List<Comment> all_comments = commentRepository.findAll();

        for (Comment comment : all_comments) {
            if (comment.getPublication().getTitle().equals(publicationDTO.getTitle())) {
                filtered_comments.add(comment);
            }
        } return filtered_comments;
    }

    public List<Comment> getAllCommentsByAuthor(UserDTO author) {
        List<Comment> filtered_comments = new ArrayList<Comment>() {};
        List<Comment> all_comments = commentRepository.findAll();

        for (Comment comment : all_comments) {
            if (comment.getAuthor().getU_name().equals(author.getName())) {
                filtered_comments.add(comment);
            }
        } return filtered_comments;
    }

    public Comment getCommentById(UUID commentId) {
        Comment comment = null;
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            comment = optionalComment.get();
        }
        return comment;
    }

    public Comment updateComment(UUID commentId, CommentUpdateDTO newComment) {
        Comment comment = null;
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            comment = optionalComment.get();
            comment.setC_text(newComment.getText());
            comment.setParent(newComment.getParent());
            comment.setPublication(newComment.getPublication());
            comment.setLast_modification(LocalDateTime.now());
            commentRepository.save(comment);
        } return comment;
    }

    public List<Comment> getAllCommentsOrdered(PublicationDTO publicationDTO) {
        List<Comment> all_comments = getAllComments(publicationDTO);
        Collections.sort(all_comments);
        return all_comments;
    }
}
