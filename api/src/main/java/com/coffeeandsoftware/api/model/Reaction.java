package com.coffeeandsoftware.api.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    @ManyToOne
    private User author;

    @ManyToOne
    private Publication r_publication;

    private String r_type;
}
