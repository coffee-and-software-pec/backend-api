package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findAllByAuthor(User user);
    List<Publication> findAllByTagsIn(Collection<List<Tag>> tags);
}