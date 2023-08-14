package com.coffeeandsoftware.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.FetchType;

@Entity
@Table(name = "publication_i")
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Publication implements Comparable<Publication>{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID p_id;

    private String title;
    private String subtitle;

    @Lob
    @Column(columnDefinition = "text")
    private String continuous_text;

    private String main_img_url;
    private boolean is_private = true;
    private boolean is_draft = true;
    private LocalDateTime creation_date;
    private LocalDateTime last_modification;
    private int visualizations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tagged_publication_i",
        joinColumns = @JoinColumn(name = "p_id"),
        inverseJoinColumns = @JoinColumn(name = "t_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User author;

    @OneToMany(mappedBy="r_publication", cascade = CascadeType.REMOVE)
    private List<Reaction> reactions = new ArrayList<>();

    @OneToMany(mappedBy="publication", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy="publication", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Denuncia> denuncia = new ArrayList<>();

    @Override
    public int compareTo(Publication arg0) {
        return creation_date.compareTo(arg0.creation_date);
    }
}
