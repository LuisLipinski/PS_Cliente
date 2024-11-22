package com.petshop.clients.controller;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.PetResponse;
import com.petshop.clients.model.Pets;
import com.petshop.clients.model.Sexo;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
