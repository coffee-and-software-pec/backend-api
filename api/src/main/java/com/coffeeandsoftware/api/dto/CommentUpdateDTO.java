package com.coffeeandsoftware.api.dto;

import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.Publication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDTO {
    private String text;
    private Comment parent;
    private Publication publication;

}
