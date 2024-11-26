package com.petshop.clients.service;

import com.petshop.clients.exception.ClienteNotFoundException;
import com.petshop.clients.model.*;
import com.petshop.clients.repository.ClienteRepository;
import com.petshop.clients.repository.PetRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class
ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PetRepository petRepository;

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente registerCliente(Cliente cliente) {
        cliente.setStatus(1);
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getAllClientes(SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        List<Cliente> clientes = clienteRepository.findByStatus(1, sort);
        if (clientes == null) {
            throw new ClienteNotFoundException("Nenhum Cliente encontrado.");
        }
        for(Cliente cliente : clientes) {
            cliente.setPets(cliente.getPets().stream().filter(pets -> pets.getStatus() == 1).collect(Collectors.toList()));
        }
        return clientes;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente getClientById(Long id) {
        Cliente cliente = clienteRepository.findByIdAndStatus(id);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        return cliente;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getClientByNomeTutor(String nomeTutor, SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        List<Cliente> clientes = clienteRepository.findByNomeTutorAndStatus(nomeTutor, sort);
        if (clientes == null) {
            throw new ClienteNotFoundException("Não foi encontrado nenhum cliente com o nome " + nomeTutor);
        }
        return clientes;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente getClientByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpfAndStatus(cpf);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o CPF: " + cpf);
        }
        return cliente;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente updateCliente(Long id, Cliente clienteAtualizado) throws ValidationException {
        Cliente existingCliente = clienteRepository.findByIdAndStatus(id);
        if(existingCliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        existingCliente.setNomeTutor(clienteAtualizado.getNomeTutor());
        existingCliente.setCpf(clienteAtualizado.getCpf());
        existingCliente.setTelefone(clienteAtualizado.getTelefone());
        existingCliente.setSexoTutor(clienteAtualizado.getSexoTutor());
        existingCliente.setCep(clienteAtualizado.getCep());
        existingCliente.setRua(clienteAtualizado.getRua());
        existingCliente.setComplemento(clienteAtualizado.getComplemento());
        existingCliente.setNumero(clienteAtualizado.getNumero());
        existingCliente.setEstado(clienteAtualizado.getEstado());
        existingCliente.setCidade(clienteAtualizado.getCidade());

        return clienteRepository.save(existingCliente);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public boolean deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findByIdAndStatus(id);
        if (cliente == null) {
            return false;
        }

        cliente.setStatus(0);
        clienteRepository.save(cliente);

        for (Pets pet : cliente.getPets()) {
            pet.setStatus(0);
        }

        petRepository.saveAll(cliente.getPets());
        return true;
    }
}
