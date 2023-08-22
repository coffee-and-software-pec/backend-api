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

    public List<Denuncia> getAll(){
        return denunciaRepository.findAll();
    }

    public Optional<Denuncia> getById(UUID denunciaId){
        return denunciaRepository.findById(denunciaId);
    }

    public Denuncia deleteById(UUID denunciaId){
        Denuncia denuncia = null;
        Optional<Denuncia> optionalDenuncia = denunciaRepository.findById(denunciaId);
        if (optionalDenuncia.isPresent()){
            denuncia = optionalDenuncia.get();
            denunciaRepository.deleteById(denunciaId);
        }

        return denuncia;
    }

    public boolean haveDenuncia(UUID userId, UUID publicationId){
        Publication publi = publicationService.getPublicationById(publicationId);
        User author = userService.getUserById(userId);

        var d = denunciaRepository.findPreviousDenuncia(publi, author);

        if (d.isEmpty()){
            return false; 
        }

        return true;
    } 
}