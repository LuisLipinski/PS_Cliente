package com.petshop.clients.controller;

import com.petshop.clients.exception.ClienteNameInvalidException;
import com.petshop.clients.exception.ClienteNotFoundException;
import com.petshop.clients.model.*;
import com.petshop.clients.service.ClienteService;
import com.petshop.clients.validation.NomeValidation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.xml.bind.ValidationException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    public static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

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
        logger.info("Iniciando o registro do cliente: {}", nomeTutor);
        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomeTutor(nomeTutor)){
            logger.error("Nome do tutor invalido.");
            throw new ClienteNameInvalidException("Nome do tutor invalido.");
        }

        try {
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

            logger.info("Cadastrando o cliente");

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
            logger.info("Cliente registrado com sucesso: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao registrar cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    @GetMapping("/buscar/all")
    public ResponseEntity<List<ClienteResponse>> getAllClientes(@RequestParam(required = false, defaultValue = "NOME_TUTOR") SortField sortField,
                                                                @RequestParam(required = false, defaultValue = "ASC") DirectionField direction) {
        logger.info("Buscando todos os clientes ordenado por: {}, {}", sortField, direction);
        try {
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

                logger.info("Buscando os pets dos cliente");

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
            logger.info("Clientes listado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Erro ao buscar clientes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        try {
            logger.info("Buscando cliente com ID: {}", id);
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

            logger.info("Buscando os pets do cliente");

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

            logger.info("Cliente filtrado com sucesso: id: {}, nome do tutor: {}, sexo tutor: {}, cpf: {}, telefone: {}, cep: {}, endereço: {}, estado: {}, cidade: {}, pets: {}",
                    clienteResponse.getId(), clienteResponse.getNomeTutor(), clienteResponse.getSexoTutor(), clienteResponse.getCpf(), clienteResponse.getTelefone(),
                    clienteResponse.getCep(), clienteResponse.getEndereco(), clienteResponse.getEstado(), clienteResponse.getCidade(), clienteResponse.getPets());
            return ResponseEntity.ok(clienteResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/buscar/nomeTutor")
    public ResponseEntity <List<ClienteResponse>> getClientesByNomeTutor(@RequestParam String nomeTutor,
                                                     @RequestParam(required = false, defaultValue = "NOME_TUTOR") SortField sortField,
                                                     @RequestParam(required = false, defaultValue = "ASC") DirectionField direction) {

        logger.info("Iniciando a busca dos clientes com o nome {}", nomeTutor);
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

            logger.info("Busando os pets do cliente");

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

        logger.info("Cliente filtrado com sucesso com o nome: {} e ordenado por {} e {}", nomeTutor, sortField, direction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/cpf")
    public ResponseEntity<ClienteResponse> getByCpf(@RequestParam String cpf) {
        logger.info("Iniciado a busca pelo cliente com cpf {}", cpf);
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

        logger.info("Filtrando os pets do cliente");

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

        logger.info("Cliente filtrado com sucesso com o cpf: {}", cpf);
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

        logger.info("Iniciando a edição do cliente");
        NomeValidation nomeValidation = new NomeValidation();
        if(!nomeValidation.isvalidNomeTutor(nomeTutor)){
            logger.error("nome do tutor invalido.");
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

        logger.info("Buscando o cliente com id {}", id);
        Cliente updateCliente = clienteService.updateCliente(id, clienteAtualizado);
        if (updateCliente == null){
            logger.info("Cliente não encontrado com o ID: {}", id);
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        ClienteResponse response = new ClienteResponse();
        logger.info("Atualizando o cliente");
        response.setId(updateCliente.getId());
        response.setNomeTutor(updateCliente.getNomeTutor());
        response.setSexoTutor(updateCliente.getSexoTutor());
        response.setCpf(updateCliente.getCpf());
        response.setTelefone(updateCliente.getTelefone());
        response.setCep(updateCliente.getCep());
        response.setEndereco(updateCliente.getEndereco());
        response.setEstado(updateCliente.getEstado());
        response.setCidade(updateCliente.getCidade());

        logger.info("Cliente com id {} foi atualizado com sucesso", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable long id) {
        logger.info("Buscando usuário com id: {}", id);
        boolean deleted = clienteService.deleteCliente(id);
        if(deleted) {
            logger.info("Cliente com id {} foi excluido", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.error("Cliente não encontrado com o ID: {}", + id);
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
    }

}
