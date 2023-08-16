package com.coffeeandsoftware.api.repositories;

import java.util.List;
import java.util.UUID;

import com.coffeeandsoftware.api.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeandsoftware.api.model.Revision;

public interface RevisionRepository extends JpaRepository<Revision, UUID> {
    List<Revision> findAllByPublication(Publication publication);
}
