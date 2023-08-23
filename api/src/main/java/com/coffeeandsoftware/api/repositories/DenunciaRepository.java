package com.coffeeandsoftware.api.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.coffeeandsoftware.api.model.Denuncia;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;
import javax.transaction.Transactional;

@Transactional
public interface DenunciaRepository extends JpaRepository<Denuncia,UUID> {
    List<Denuncia> findAllByPublication(Publication publication);
    List<Denuncia> findAllByAuthor(User author);

    @Query("SELECT d FROM Denuncia d WHERE d.publication = :publication and d.author = :author")
    List<Denuncia> findPreviousDenuncia(@Param("publication") Publication publication, @Param("author") User author);
}