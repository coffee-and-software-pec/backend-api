package com.coffeeandsoftware.api.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.User;

public interface CommentRepository extends JpaRepository<Comment,UUID> {
    List<Comment> findAllByAuthor(User user);
    List<Comment> findAllByPublication(Publication publication);
    List<Comment> findAllByParent(Comment parent);
}
