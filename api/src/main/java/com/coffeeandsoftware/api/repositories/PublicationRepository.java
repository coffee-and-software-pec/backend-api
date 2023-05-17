package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
