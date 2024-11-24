package com.petshop.clients.service;

import com.petshop.clients.model.*;
import com.petshop.clients.repository.ClienteRepository;
import com.petshop.clients.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Pets> getAllPets(SortFieldPets sortFieldPets, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortFieldPets.getFieldPets());
        return petRepository.findAll(sort);
    }

    public Pets getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
}
