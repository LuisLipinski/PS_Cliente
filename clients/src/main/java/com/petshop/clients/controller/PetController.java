package com.petshop.clients.controller;

import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.service.PetService;
import com.petshop.clients.validation.NomeValidation;
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
}
