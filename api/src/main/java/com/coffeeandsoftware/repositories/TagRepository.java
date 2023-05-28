package com.coffeeandsoftware.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeandsoftware.model.Publication;
import com.coffeeandsoftware.model.Tag;


public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findByPublication(Publication pub);
}
