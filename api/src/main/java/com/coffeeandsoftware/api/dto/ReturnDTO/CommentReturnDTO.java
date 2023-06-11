package com.coffeeandsoftware.api.dto.ReturnDTO;

import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReturnDTO {
    UUID c_id;
    User author;
    LocalDateTime creation_date;
    String c_text;
    public CommentReturnDTO(Comment comment) {
        this.c_id = comment.getC_id();
        this.author = comment.getAuthor();
        this.creation_date = comment.getCreation_date();
        this.c_text = comment.getC_text();
    }
}
