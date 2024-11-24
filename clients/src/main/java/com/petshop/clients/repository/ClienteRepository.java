package com.petshop.clients.repository;

import com.petshop.clients.model.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeTutor(String nomeTutor, Sort sort);
}
