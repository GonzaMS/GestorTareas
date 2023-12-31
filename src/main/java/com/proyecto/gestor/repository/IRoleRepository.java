package com.proyecto.gestor.repository;

import com.proyecto.gestor.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);
}
