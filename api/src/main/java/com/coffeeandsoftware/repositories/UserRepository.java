package com.coffeeandsoftware.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeandsoftware.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
