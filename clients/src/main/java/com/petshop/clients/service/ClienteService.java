package com.petshop.clients.service;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.ClienteResponse;
import com.petshop.clients.model.DirectionField;
import com.petshop.clients.model.SortField;
import com.petshop.clients.repository.ClienteRepository;
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

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente registerCliente(Cliente cliente) {
        cliente.setStatus(1);
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getAllClientes(SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        List<Cliente> clientes = clienteRepository.findByStatus(1, sort);
        for(Cliente cliente : clientes) {
            cliente.setPets(cliente.getPets().stream().filter(pets -> pets.getStatus() == 1).collect(Collectors.toList()));
        }
        return clientes;
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente getClientById(Long id) {
        return clienteRepository.findByIdAndStatus(id);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public List<Cliente> getClientByNomeTutor(String nomeTutor, SortField sortField, DirectionField directionField) {
        Sort sort = Sort.by(Sort.Direction.fromString(directionField.getDirection()),sortField.getField());
        return clienteRepository.findByNomeTutorAndStatus(nomeTutor, sort);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente getClientByCpf(String cpf) {
        return clienteRepository.findByCpfAndStatus(cpf);
    }

    @PreAuthorize("hasRole('MASTER') or hasRole('ADMIN') or hasRole('LOJA')")
    public Cliente updateCliente(Long id, Cliente clienteAtualizado) throws ValidationException {
        Cliente existingCliente = clienteRepository.findByIdAndStatus(id);
        if(existingCliente == null) {
            return null;
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
}
