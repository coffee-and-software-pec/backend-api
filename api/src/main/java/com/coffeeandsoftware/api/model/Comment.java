package com.coffeeandsoftware.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_i")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Comparable<Comment> {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID c_id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Publication publication;

    @ManyToOne
    private Comment parent;

    private String c_text;
    private LocalDateTime creation_date;
    private LocalDateTime last_modification;
    @Override
    public int compareTo(Comment o) {
        return creation_date.compareTo(o.creation_date);
    }
}
