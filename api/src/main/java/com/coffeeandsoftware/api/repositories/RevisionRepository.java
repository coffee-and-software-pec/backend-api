package com.coffeeandsoftware.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeandsoftware.api.model.Revision;

public interface RevisionRepository extends JpaRepository<Revision, UUID> {
    
}
