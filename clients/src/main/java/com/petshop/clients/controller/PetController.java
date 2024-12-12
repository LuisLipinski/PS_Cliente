package com.petshop.clients.controller;

import com.petshop.clients.exception.PetNotFoundException;
import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.service.PetService;
import com.petshop.clients.validation.NomeValidation;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    private ClienteService clienteService;

    @PostMapping("/register")
    public ResponseEntity<PetResponse> register(@RequestParam Long clienteId,
                                                @RequestParam String nomePet,
                                                @RequestParam String tipoPet,
                                                @RequestParam String racaPet,
                                                @RequestParam String corPet,
                                                @RequestParam SexoPet sexoPet) {

        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomePet(nomePet)) {
            return ResponseEntity.badRequest().body(null);
        }
        Pets pet = new Pets();
        pet.setNomePet(nomePet);
        pet.setTipoPet(tipoPet);
        pet.setRacaPet(racaPet);
        pet.setCorPet(corPet);
        pet.setSexoPet(sexoPet);

        Pets newPet = petService.registerPet(pet, clienteId);

        PetResponse response = new PetResponse();
        response.setId(newPet.getId());
        response.setNomePet(newPet.getNomePet());
        response.setTipoPet(newPet.getTipoPet());
        response.setRacaPet(newPet.getRacaPet());
        response.setCorPet(newPet.getCorPet());
        response.setSexoPet(newPet.getSexoPet());

        return  ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/all")
    public ResponseEntity<List<PetResponse>> getAllPets(SortFieldPets sortFieldPets, DirectionField directionField) {
        List<Pets> pets = petService.getAllPets(sortFieldPets, directionField);
            List<PetResponse> petResponses = pets.stream().map(pet -> {
                PetResponse petResponse = new PetResponse();
                petResponse.setId(pet.getId());
                petResponse.setNomePet(pet.getNomePet());
                petResponse.setTipoPet(pet.getTipoPet());
                petResponse.setRacaPet(pet.getRacaPet());
                petResponse.setSexoPet(pet.getSexoPet());
                petResponse.setCorPet(pet.getCorPet());
                return petResponse;
            }).collect(Collectors.toList());
        return ResponseEntity.ok(petResponses);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PetResponse> getPetById(Long id) {
        Pets pets = petService.getPetById(id);
        PetResponse petResponse = new PetResponse();
        petResponse.setId(pets.getId());
        petResponse.setNomePet(pets.getNomePet());
        petResponse.setTipoPet(pets.getTipoPet());
        petResponse.setRacaPet(pets.getRacaPet());
        petResponse.setSexoPet(pets.getSexoPet());
        petResponse.setCorPet(pets.getCorPet());
        return ResponseEntity.ok(petResponse);
    }

    @GetMapping("/buscar/nomePet")
    public ResponseEntity<List<PetResponse>> getnomePets(String nomePets, SortFieldPets sortFieldPets, DirectionField directionField) {
        List<Pets> pets = petService.getpetByName(nomePets, sortFieldPets, directionField);
        List<PetResponse> petResponses = pets.stream().map(pet -> {
            PetResponse petResponse = new PetResponse();
            petResponse.setId(pet.getId());
            petResponse.setNomePet(pet.getNomePet());
            petResponse.setTipoPet(pet.getTipoPet());
            petResponse.setRacaPet(pet.getRacaPet());
            petResponse.setSexoPet(pet.getSexoPet());
            petResponse.setCorPet(pet.getCorPet());
            return petResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(petResponses);
    }

    @GetMapping("/buscar/tipoPet")
    public ResponseEntity<List<PetResponse>> getTipoPet(String tipoPet, SortFieldPets sortFieldPets, DirectionField directionField) {
        List<Pets> pets = petService.getpetByTipo(tipoPet, sortFieldPets, directionField);
        List<PetResponse> petResponses = pets.stream().map(pet -> {
            PetResponse petResponse = new PetResponse();
            petResponse.setId(pet.getId());
            petResponse.setNomePet(pet.getNomePet());
            petResponse.setTipoPet(pet.getTipoPet());
            petResponse.setRacaPet(pet.getRacaPet());
            petResponse.setSexoPet(pet.getSexoPet());
            petResponse.setCorPet(pet.getCorPet());
            return petResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(petResponses);
    }

    @GetMapping("/buscar/raca")
    public ResponseEntity<List<PetResponse>> getraca(String raca, SortFieldPets sortFieldPets, DirectionField directionField) {
        List<Pets> pets = petService.getpetByraca(raca, sortFieldPets, directionField);
        List<PetResponse> petResponses = pets.stream().map(pet -> {
            PetResponse petResponse = new PetResponse();
            petResponse.setId(pet.getId());
            petResponse.setNomePet(pet.getNomePet());
            petResponse.setTipoPet(pet.getTipoPet());
            petResponse.setRacaPet(pet.getRacaPet());
            petResponse.setSexoPet(pet.getSexoPet());
            petResponse.setCorPet(pet.getCorPet());
            return petResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(petResponses);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long id,
                                                @RequestParam String nomePet,
                                                @RequestParam String tipoPet,
                                                @RequestParam String racaPet,
                                                @RequestParam String corPet,
                                                @RequestParam SexoPet sexoPet) throws ValidationException {

        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomePet(nomePet)) {
            return ResponseEntity.badRequest().body(null);
        }
        Pets petAtualizado = new Pets();
        petAtualizado.setNomePet(nomePet);
        petAtualizado.setTipoPet(tipoPet);
        petAtualizado.setRacaPet(racaPet);
        petAtualizado.setCorPet(corPet);
        petAtualizado.setSexoPet(sexoPet);

        Pets newPet = petService.updatePet(id, petAtualizado);
        if (newPet == null) {
            throw new PetNotFoundException("Pet não encontrado com o id " + id);
        }

        PetResponse response = new PetResponse();
        response.setId(newPet.getId());
        response.setNomePet(newPet.getNomePet());
        response.setTipoPet(newPet.getTipoPet());
        response.setRacaPet(newPet.getRacaPet());
        response.setCorPet(newPet.getCorPet());
        response.setSexoPet(newPet.getSexoPet());

        return  ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable long id) {
        boolean deleted = petService.deletePet(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new PetNotFoundException("Pet não encontrado com o id " + id);
        }
    }

}
