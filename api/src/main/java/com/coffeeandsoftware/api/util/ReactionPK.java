package com.coffeeandsoftware.api.util;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ReactionPK implements Serializable{
    private UUID userId;
    private UUID publicationId;
}
