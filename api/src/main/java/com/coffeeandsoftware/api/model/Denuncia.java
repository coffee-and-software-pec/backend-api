package com.coffeeandsoftware.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;
import java.time.LocalDateTime;
import javax.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "denuncia_i")
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Denuncia {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID d_id; 

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p_id", nullable = false)
    private Publication publication; 

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "u_id", nullable = false)
    private User author; 

    private String text; 
    private LocalDateTime creation_date;


    public void setPublication(Publication publi){
        this.publication = publi;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setAuthor(User author){
        this.author = author;
    }

    public void setCreationDate(LocalDateTime time){
        this.creation_date = time;
    }

}

