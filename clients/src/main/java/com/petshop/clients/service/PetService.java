package com.petshop.clients.service;

import com.petshop.clients.exception.ClienteNotFoundException;
import com.petshop.clients.exception.PetNotFoundException;
import com.petshop.clients.model.*;
import com.petshop.clients.repository.ClienteRepository;
import com.petshop.clients.repository.PetRepository;
import jakarta.xml.bind.ValidationException;
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
        pet.setStatus(1);
        return petRepository.save(pet);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Pets> getAllPets(SortFieldPets sortFieldPets, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortFieldPets.getFieldPets());
        List<Pets> pets = petRepository.findByStatus(1, sort);
        if (pets == null) {
            throw new PetNotFoundException("Nenhum Pet encontrado.");
        }
        return pets;
    }

    public Pets getPetById(Long id) {

        Pets pet = petRepository.findByIdAndStatus(id);
        if (pet == null) {
            throw new PetNotFoundException("Pet não encontrado com o id " + id);
        }
        return pet;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Pets> getpetByName(String nomePet, SortFieldPets sortFieldPets, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortFieldPets.getFieldPets());
        List<Pets> pets = petRepository.findByNomePetAndStatus(nomePet, 1, sort);
        if (pets == null) {
            throw new PetNotFoundException("Nenhum pet encontrado com o nome " + nomePet);
        }
        return pets;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Pets> getpetByTipo(String tipo, SortFieldPets sortFieldPets, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortFieldPets.getFieldPets());
        List<Pets> pets = petRepository.findByTipoPetAndStatus(tipo, 1, sort);
        if (pets == null) {
            throw new PetNotFoundException("Nenhum pet encontrado com o nome " + tipo);
        }
        return pets;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Pets> getpetByraca(String raca, SortFieldPets sortFieldPets, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortFieldPets.getFieldPets());
        List<Pets> pets = petRepository.findByRacaPetAndStatus(raca, 1, sort);
        if (pets == null) {
            throw new PetNotFoundException("Nenhum pet encontrado com o nome " + raca);
        }
        return pets;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Pets updatePet(Long id, Pets petAtualizado) throws ValidationException {
        Pets existingPet = petRepository.findByIdAndStatus(id);
        if(existingPet == null) {
            return null;
        }
        existingPet.setNomePet(petAtualizado.getNomePet());
        existingPet.setTipoPet(petAtualizado.getTipoPet());
        existingPet.setRacaPet(petAtualizado.getRacaPet());
        existingPet.setSexoPet(petAtualizado.getSexoPet());
        existingPet.setCorPet(petAtualizado.getCorPet());


        return petRepository.save(existingPet);
    }
    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public boolean deletePet(long id) {
        Pets pets = petRepository.findByIdAndStatus(id);
        if (pets == null) {
            return false;
        }
        pets.setStatus(0);
        petRepository.save(pets);
        return true;
    }
}
