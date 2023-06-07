package com.coffeeandsoftware.api.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO {
    private UUID author_id;
    private String publication_id;
    private String type;
}
