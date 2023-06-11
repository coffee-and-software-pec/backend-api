package com.coffeeandsoftware.api.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import com.coffeeandsoftware.api.util.ReactionPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "reaction_i")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    @EmbeddedId
    private ReactionPK id = new ReactionPK();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @MapsId("publicationId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publication r_publication;

    private String r_type;
}
