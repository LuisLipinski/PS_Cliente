package com.petshop.clients.repository;

import com.petshop.clients.model.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pets, Long> {
}
