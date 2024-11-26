package com.petshop.clients.controller;

import com.petshop.clients.exception.ClienteNameInvalidException;
import com.petshop.clients.exception.ClienteNotFoundException;
import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.validation.NomeValidation;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/register")
    public ResponseEntity<ClienteResponse> register(@RequestParam String nomeTutor,
                                                    @RequestParam Sexo sexoTutor,
                                                    @RequestParam String cpf,
                                                    @RequestParam String telefone,
                                                    @RequestParam String cep,
                                                    @RequestParam String rua,
                                                    @RequestParam(required = false, defaultValue = "") String complemento,
                                                    @RequestParam String numero,
                                                    @RequestParam Estado estado,
                                                    @RequestParam String cidade) throws ValidationException {
        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomeTutor(nomeTutor)){
            throw new ClienteNameInvalidException("Nome do tutor invalido.");
        }
        Cliente cliente = new Cliente();
        cliente.setNomeTutor(nomeTutor);
        cliente.setSexoTutor(sexoTutor);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setCep(cep);
        if(complemento != null && !complemento.trim().isEmpty()) {
            cliente.setEndereco(rua, complemento, numero);
        } else {
            cliente.setEndereco(rua, numero);
        }
        cliente.setEstado(estado);
        cliente.setCidade(cidade);

        Cliente newCliente = clienteService.registerCliente(cliente);

        ClienteResponse response = new ClienteResponse();
        response.setId(newCliente.getId());
        response.setNomeTutor(newCliente.getNomeTutor());
        response.setSexoTutor(newCliente.getSexoTutor());
        response.setCpf(newCliente.getCpf());
        response.setTelefone(newCliente.getTelefone());
        response.setCep(newCliente.getCep());
        response.setEndereco(newCliente.getEndereco());
        response.setEstado(newCliente.getEstado());
        response.setCidade(newCliente.getCidade());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/all")
    public ResponseEntity<List<ClienteResponse>> getAllClientes(@RequestParam(required = false, defaultValue = "NOME_TUTOR") SortField sortField,
                                                                @RequestParam(required = false, defaultValue = "ASC") DirectionField direction) {
        List<Cliente> clientes = clienteService.getAllClientes(sortField, direction);
        List<ClienteResponse> response = clientes.stream().map(cliente -> {
            ClienteResponse clienteResponse = new ClienteResponse();
            clienteResponse.setId(cliente.getId());
            clienteResponse.setNomeTutor(cliente.getNomeTutor());
            clienteResponse.setSexoTutor(cliente.getSexoTutor());
            clienteResponse.setCpf(cliente.getCpf());
            clienteResponse.setTelefone(cliente.getTelefone());
            clienteResponse.setCep(cliente.getCep());
            clienteResponse.setEndereco(cliente.getEndereco());
            clienteResponse.setEstado(cliente.getEstado());
            clienteResponse.setCidade(cliente.getCidade());

            List<PetResponse> petResponses = cliente.getPets().stream().map(pets -> {
                PetResponse petResponse = new PetResponse();
                petResponse.setId(pets.getId());
                petResponse.setNomePet(pets.getNomePet());
                petResponse.setTipoPet(pets.getTipoPet());
                petResponse.setRacaPet(pets.getRacaPet());
                petResponse.setSexoPet(pets.getSexoPet());
                petResponse.setCorPet(pets.getCorPet());
                return petResponse;
            }).collect(Collectors.toList());
            clienteResponse.setPets(petResponses);
            return clienteResponse;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClientById(id);
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setId(cliente.getId());
        clienteResponse.setNomeTutor(cliente.getNomeTutor());
        clienteResponse.setSexoTutor(cliente.getSexoTutor());
        clienteResponse.setCpf(cliente.getCpf());
        clienteResponse.setTelefone(cliente.getTelefone());
        clienteResponse.setCep(cliente.getCep());
        clienteResponse.setEndereco(cliente.getEndereco());
        clienteResponse.setEstado(cliente.getEstado());
        clienteResponse.setCidade(cliente.getCidade());

        List<PetResponse> petResponses = cliente.getPets().stream().filter(pet -> pet.getStatus() == 1).map(pets -> {
            PetResponse petResponse = new PetResponse();
            petResponse.setId(pets.getId());
            petResponse.setNomePet(pets.getNomePet());
            petResponse.setTipoPet(pets.getTipoPet());
            petResponse.setRacaPet(pets.getRacaPet());
            petResponse.setSexoPet(pets.getSexoPet());
            petResponse.setCorPet(pets.getCorPet());
            return petResponse;
        }).collect(Collectors.toList());
        clienteResponse.setPets(petResponses);


        return ResponseEntity.ok(clienteResponse);
    }

    @GetMapping("/buscar/nomeTutor")
    public ResponseEntity <List<ClienteResponse>> getClientesByNomeTutor(@RequestParam String nomeTutor,
                                                     @RequestParam(required = false, defaultValue = "NOME_TUTOR") SortField sortField,
                                                     @RequestParam(required = false, defaultValue = "ASC") DirectionField direction) {
        List<Cliente> clientes = clienteService.getClientByNomeTutor(nomeTutor, sortField, direction);
        List<ClienteResponse> response = clientes.stream().map(cliente -> {
            ClienteResponse clienteResponse = new ClienteResponse();
            clienteResponse.setId(cliente.getId());
            clienteResponse.setNomeTutor(cliente.getNomeTutor());
            clienteResponse.setSexoTutor(cliente.getSexoTutor());
            clienteResponse.setCpf(cliente.getCpf());
            clienteResponse.setTelefone(cliente.getTelefone());
            clienteResponse.setCep(cliente.getCep());
            clienteResponse.setEndereco(cliente.getEndereco());
            clienteResponse.setEstado(cliente.getEstado());
            clienteResponse.setCidade(cliente.getCidade());

            List<PetResponse> petResponses = cliente.getPets().stream().filter(pet -> pet.getStatus() == 1).map(pets -> {
                PetResponse petResponse = new PetResponse();
                petResponse.setId(pets.getId());
                petResponse.setNomePet(pets.getNomePet());
                petResponse.setTipoPet(pets.getTipoPet());
                petResponse.setRacaPet(pets.getRacaPet());
                petResponse.setSexoPet(pets.getSexoPet());
                petResponse.setCorPet(pets.getCorPet());
                return petResponse;
            }).collect(Collectors.toList());
            clienteResponse.setPets(petResponses);
            return clienteResponse;
        }).collect(Collectors.toList());


        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/cpf")
    public ResponseEntity<ClienteResponse> getByCpf(@RequestParam String cpf) {
        Cliente cliente = clienteService.getClientByCpf(cpf);
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setId(cliente.getId());
        clienteResponse.setNomeTutor(cliente.getNomeTutor());
        clienteResponse.setSexoTutor(cliente.getSexoTutor());
        clienteResponse.setCpf(cliente.getCpf());
        clienteResponse.setTelefone(cliente.getTelefone());
        clienteResponse.setCep(cliente.getCep());
        clienteResponse.setEndereco(cliente.getEndereco());
        clienteResponse.setEstado(cliente.getEstado());
        clienteResponse.setCidade(cliente.getCidade());

        List<PetResponse> petResponses = cliente.getPets().stream().filter(pet -> pet.getStatus() == 1).map(pets -> {
            PetResponse petResponse = new PetResponse();
            petResponse.setId(pets.getId());
            petResponse.setNomePet(pets.getNomePet());
            petResponse.setTipoPet(pets.getTipoPet());
            petResponse.setRacaPet(pets.getRacaPet());
            petResponse.setSexoPet(pets.getSexoPet());
            petResponse.setCorPet(pets.getCorPet());
            return petResponse;
        }).collect(Collectors.toList());
        clienteResponse.setPets(petResponses);


        return ResponseEntity.ok(clienteResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteResponse> updateClient(@PathVariable Long id,
                                                    @RequestParam String nomeTutor,
                                                    @RequestParam Sexo sexoTutor,
                                                    @RequestParam String cpf,
                                                    @RequestParam String telefone,
                                                    @RequestParam String cep,
                                                    @RequestParam String rua,
                                                    @RequestParam(required = false, defaultValue = "") String complemento,
                                                    @RequestParam String numero,
                                                    @RequestParam Estado estado,
                                                    @RequestParam String cidade) throws ValidationException {
        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomeTutor(nomeTutor)){
            throw new ClienteNameInvalidException("Nome do tutor invalido.");
        }
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeTutor(nomeTutor);
        clienteAtualizado.setSexoTutor(sexoTutor);
        clienteAtualizado.setCpf(cpf);
        clienteAtualizado.setTelefone(telefone);
        clienteAtualizado.setCep(cep);
        if(complemento != null && !complemento.trim().isEmpty()) {
            clienteAtualizado.setEndereco(rua, complemento, numero);
        } else {
            clienteAtualizado.setEndereco(rua, numero);
        }
        clienteAtualizado.setEstado(estado);
        clienteAtualizado.setCidade(cidade);

        Cliente updateCliente = clienteService.updateCliente(id, clienteAtualizado);
        if (updateCliente == null){
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        ClienteResponse response = new ClienteResponse();
        response.setId(updateCliente.getId());
        response.setNomeTutor(updateCliente.getNomeTutor());
        response.setSexoTutor(updateCliente.getSexoTutor());
        response.setCpf(updateCliente.getCpf());
        response.setTelefone(updateCliente.getTelefone());
        response.setCep(updateCliente.getCep());
        response.setEndereco(updateCliente.getEndereco());
        response.setEstado(updateCliente.getEstado());
        response.setCidade(updateCliente.getCidade());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable long id) {
        boolean deleted = clienteService.deleteCliente(id);
        if(deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
    }

}
