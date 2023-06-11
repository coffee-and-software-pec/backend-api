package com.coffeeandsoftware.api.util;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionPK implements Serializable{
    private UUID userId;
    private UUID publicationId;
}
