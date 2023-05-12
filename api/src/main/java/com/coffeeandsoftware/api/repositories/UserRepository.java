package com.coffeeandsoftware.api.repositories;

import com.coffeeandsoftware.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
