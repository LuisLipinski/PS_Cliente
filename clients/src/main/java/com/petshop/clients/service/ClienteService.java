package com.petshop.clients.service;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.ClienteResponse;
import com.petshop.clients.model.DirectionField;
import com.petshop.clients.model.SortField;
import com.petshop.clients.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class
ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente registerCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getAllClientes(SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        return clienteRepository.findAll(sort);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente getClientById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getClientByNomeTutor(String nomeTutor, SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        return clienteRepository.findByNomeTutor(nomeTutor, sort);
    }
}
