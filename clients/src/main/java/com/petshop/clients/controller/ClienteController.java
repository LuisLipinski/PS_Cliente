package com.petshop.clients.controller;

import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

}
