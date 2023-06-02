package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Tag;
import com.coffeeandsoftware.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    List<Publication> findAllByAuthor(User user);
//    List<Publication> findAllByTagsIn(Collection<List<Tag>> tags);
    @Query("SELECT p FROM Publication p WHERE p.tags IN (:tags)")
    List<Publication> findAllByTags(@Param("tags") List<String> tags);
}