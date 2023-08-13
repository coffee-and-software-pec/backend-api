package com.coffeeandsoftware.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "denuncia_i")
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Denuncia {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID d_id; 

    @ManyToOne
    @JoinColumn(name = "p_id", nullable = false)
    private Publication publication; 

    @ManyToOne
    @JoinColumn(name = "u_id", nullable = false)
    private User author_d; 


    private String d_text; 
    private LocalDateTime creation_date;

}