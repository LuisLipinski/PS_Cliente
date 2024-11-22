package com.petshop.clients.service;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.Pets;
import com.petshop.clients.repository.ClienteRepository;
import com.petshop.clients.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Pets registerPet(Pets pet, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado com o ID: " + clienteId));
        pet.setCliente(cliente);
        return petRepository.save(pet);
    }
}
