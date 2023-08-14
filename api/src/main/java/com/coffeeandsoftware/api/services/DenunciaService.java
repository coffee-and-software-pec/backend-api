package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.model.Denuncia;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.DenunciaRepository;
import com.coffeeandsoftware.api.services.PublicationService;
import com.coffeeandsoftware.api.services.UserService;   
import com.coffeeandsoftware.api.dto.DenunciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DenunciaService {
    @Autowired 
    DenunciaRepository denunciaRepository; 

    @Autowired
    PublicationService publicationService;

    @Autowired
    UserService userService;

    public Denuncia createDenuncia(DenunciaDTO d) throws Exception {
        Publication publi = publicationService.getPublicationById(d.getPublication());
        User author = userService.getUserById(d.getAuthor());

        // Check if author and publication are valids. 
        if (publi != null && author != null ){
            
            // Check if author alredy make a denuncia to same publication
            var previous = denunciaRepository.findPreviousDenuncia(publi, author);
            if(previous.isEmpty()){
                Denuncia denuncia = new Denuncia(); 
                denuncia.setPublication(publi);
                denuncia.setAuthor(author);
                denuncia.setText(d.getText());
                denuncia.setCreationDate(LocalDateTime.now());

                denunciaRepository.save(denuncia);
                return denuncia;
            }else {
                throw new Exception();
            }

        }

        return null ;        
    }





}