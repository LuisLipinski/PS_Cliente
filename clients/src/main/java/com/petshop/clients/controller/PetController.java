package com.petshop.clients.controller;

import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.service.PetService;
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
                                                @RequestParam Sexo sexoPet) {
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
}
