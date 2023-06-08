package com.coffeeandsoftware.api.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.util.ReactionPK;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,ReactionPK> {
    @Query("SELECT r FROM Reaction r WHERE r.id.userId = :userId")
    List<Reaction> findAllByAuthor(@Param("userId") String userId);

    @Query("SELECT r FROM Reaction r WHERE r.id.publicationId = :publicationId")
    List<Reaction> findAllByPublication(@Param("publicationId") String publicationId);

    @Query("SELECT r FROM Reaction r WHERE r.id.userId = :userId and r.id.publicationId = :publicationId")
    Optional<Reaction> findByIds(@Param("userId") String userId, @Param("publicationId") String publicationId);
}
