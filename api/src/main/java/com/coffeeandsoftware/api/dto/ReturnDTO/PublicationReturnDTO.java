package com.coffeeandsoftware.api.dto.ReturnDTO;

import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.model.Tag;
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
public class PublicationReturnDTO {
    private UUID p_id;
    private String title;
    private String subtitle;
    private String continuous_text;
    private String main_img_url;
    private boolean is_private;
    private boolean is_draft;
    private LocalDateTime creation_date;
    private int visualizationsCount;
    private List<TagReturnDTO> tags;
    private User author;
    private List<ReactionDTO> reactions;
    private int heartsCount;
    private int commentsCount;
    public PublicationReturnDTO(Publication publication) {
        this.p_id = publication.getP_id();
        this.title = publication.getTitle();
        this.subtitle = publication.getSubtitle();
        this.continuous_text = publication.getContinuous_text();
        this.main_img_url = publication.getMain_img_url();
        this.is_private = publication.is_private();
        this.is_draft = publication.is_draft();
        this.creation_date = publication.getCreation_date();
        this.visualizationsCount = publication.getVisualizations();
        this.tags = publication.getTags().stream()
                .map(t -> new TagReturnDTO(t.getTitle()))
                .collect(Collectors.toList());
        this.author = publication.getAuthor();
        this.heartsCount = publication.getReactions().size();
        this.commentsCount = 0;
    }
}
