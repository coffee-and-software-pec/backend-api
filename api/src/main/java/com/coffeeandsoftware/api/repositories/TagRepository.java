package com.coffeeandsoftware.api.repositories;

import java.util.Optional;
import java.util.UUID;
import com.coffeeandsoftware.api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByTitle(String title);
}
