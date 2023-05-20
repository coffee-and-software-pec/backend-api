package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByUser(User user);
}