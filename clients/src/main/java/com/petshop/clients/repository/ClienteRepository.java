package com.petshop.clients.repository;

import com.petshop.clients.model.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.id = :id AND c.status = 1")
    Cliente findByIdAndStatus(@Param("id") Long id);
    @Query("SELECT c FROM Cliente c WHERE c.nomeTutor LIKE %:nomeTutor% AND c.status = 1")
    List<Cliente> findByNomeTutorAndStatus(@Param("nomeTutor") String nomeTutor, Sort sort);
    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf AND c.status = 1")
    Cliente findByCpfAndStatus(@Param("cpf") String cpf);
    List<Cliente> findByStatus(int status, Sort sort);
}
