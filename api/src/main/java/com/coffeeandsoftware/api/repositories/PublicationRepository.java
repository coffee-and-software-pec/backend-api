package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    List<Publication> findAllByAuthor(User user);
    @Query("SELECT p FROM Publication p WHERE p.tags IN (:tags)")
    List<Publication> findAllByTags(@Param("tags") List<String> tags);

    // @Query("SELECT pub FROM Publication pub WHERE (:tags) IN pub.tags")
    // List<Publication> findAllWithTag(@Param("tags") Tag soughtTag);
}