package com.coffeeandsoftware.api.dto.ReturnDTO;

import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReturnDTO {
    UUID c_id;
    User author;
    LocalDateTime creation_date;
    String c_text;
    String c_parent_id;
    List<CommentReturnDTO> replies = new ArrayList<>();
    public CommentReturnDTO(Comment comment) {
        this.c_id = comment.getC_id();
        this.author = comment.getAuthor();
        this.creation_date = comment.getCreation_date();
        this.c_text = comment.getC_text();
        this.c_parent_id = comment.getParent() == null ? "" : comment.getParent().getC_id().toString();
    }

    public CommentReturnDTO(Comment comment, List<Comment> replies) {
        this.c_id = comment.getC_id();
        this.author = comment.getAuthor();
        this.creation_date = comment.getCreation_date();
        this.c_text = comment.getC_text();
        this.replies = replies.stream().map(c -> new CommentReturnDTO(c)).collect(Collectors.toList());
        this.c_parent_id = comment.getParent() == null ? "" : comment.getParent().getC_id().toString();
    }
}
