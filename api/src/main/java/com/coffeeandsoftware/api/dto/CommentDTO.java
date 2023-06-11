package com.coffeeandsoftware.api.dto;

import java.util.UUID;

import com.coffeeandsoftware.api.model.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String author_id;
    private String publication_id;
    private String c_text;
    private String c_parent_id;
}
