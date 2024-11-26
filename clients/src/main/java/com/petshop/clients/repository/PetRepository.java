package com.petshop.clients.repository;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.Pets;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pets, Long> {
    @Query("SELECT p FROM Pets p WHERE p.id = :id AND p.status = 1")
    Pets findByIdAndStatus(@Param("id") Long id);
    @Query("SELECT p FROM Pets p WHERE p.nomePet LIKE :nomePet AND p.status = :status")
    List<Pets> findByNomePetAndStatus(@Param("nomePet") String nomePet, @Param("status") int status, Sort sort);
    @Query("SELECT p FROM Pets p WHERE p.tipoPet LIKE :tipo AND p.status = :status")
    List<Pets> findByTipoPetAndStatus(@Param("tipo")String tipo, @Param("status") int status, Sort sort);
    @Query("SELECT p FROM Pets p WHERE p.racaPet LIKE :raca AND p.status = :status")
    List<Pets> findByRacaPetAndStatus(@Param("raca") String raca, @Param("status") int status, Sort sort);
    List<Pets> findByStatus(int status, Sort sort);
}
