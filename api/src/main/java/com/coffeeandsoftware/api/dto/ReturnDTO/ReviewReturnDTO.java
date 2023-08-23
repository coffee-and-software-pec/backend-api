package com.coffeeandsoftware.api.dto.ReturnDTO;

import com.coffeeandsoftware.api.model.Revision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReturnDTO {
    private UUID r_id;
    private String text;
    private UserReturnDTO author;
    private LocalDateTime date;
    private String comment;

    public ReviewReturnDTO(Revision revision) {
        this.r_id = revision.getR_id();
        this.text = revision.getText();
        this.author = new UserReturnDTO(revision.getAuthor());
        this.date = revision.getDate();
        this.comment = revision.getComment();
    }
}
