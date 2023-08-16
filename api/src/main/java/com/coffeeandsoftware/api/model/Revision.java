package com.coffeeandsoftware.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.coffeeandsoftware.api.controller.UserController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "revision_i")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Revision {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID r_id;

    private String text;
    private User author;
    private LocalDateTime date;
    private String comment;

    public Revision(String text, User author, String comment, LocalDateTime date) {
        this.text = text;
        this.author = author;
        this.comment = comment;
        this.date = date;
    }
}
