package com.coffeeandsoftware.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coffeeandsoftware.model.Publication;
import com.coffeeandsoftware.model.Tag;
import com.coffeeandsoftware.model.User;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByUser(User user);
    List<Publication> findByTag(Tag tag);
}