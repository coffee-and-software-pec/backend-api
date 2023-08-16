package com.coffeeandsoftware.api.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeeandsoftware.api.model.Revision;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.RevisionRepository;

@Service
public class RevisionService {
    @Autowired
    RevisionRepository revisionRepository;

    public Revision createRevision(String text, User author, String comment, LocalDateTime date){
        Revision revision = new Revision(text, author, comment, date);
        return revisionRepository.save(revision);
    }
}
