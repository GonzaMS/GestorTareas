package com.proyecto.gestor.repository;

import com.proyecto.gestor.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
