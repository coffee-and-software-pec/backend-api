package com.coffeeandsoftware.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "publication_i")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID p_id;

    private String title;
    private String subtitle;
    private String continuous_text;
    private String main_img_url;
    private boolean is_private = true;
    private boolean is_draft = true;
    private LocalDateTime creation_date;
    private LocalDateTime last_modification;
    private int visualizations;

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User author;

    
}
