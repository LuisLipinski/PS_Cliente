package com.petshop.clients.service;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente registerCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
