package com.coffeeandsoftware.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.coffeeandsoftware.api.model.Reaction;
import com.coffeeandsoftware.api.util.ReactionPK;

public interface ReactionRepository extends JpaRepository<Reaction,ReactionPK> {
    @Query("SELECT r FROM Reaction r WHERE r.author.u_id = :userId")
    List<Reaction> findAllByAuthor(@Param("userId") UUID userId);

    @Query("SELECT r FROM Reaction r WHERE r.r_publication.p_id = :publicationId")
    List<Reaction> findAllByPublication(@Param("publicationId") UUID publicationId);

    @Query("SELECT r FROM Reaction r WHERE r.r_publication.p_id = :userId and r.author.u_id = :publicationId")
    Optional<Reaction> findByIds(@Param("userId") UUID userId, @Param("publicationId") UUID publicationId);

    Optional<Reaction> findReactionById(ReactionPK reactionPK);
}
