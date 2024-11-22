package com.petshop.clients.controller;

import com.petshop.clients.model.Cliente;
import com.petshop.clients.model.ClienteResponse;
import com.petshop.clients.model.Estado;
import com.petshop.clients.model.Sexo;
import com.petshop.clients.service.ClienteService;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                                    @RequestParam String complemento,
                                                    @RequestParam String numero,
                                                    @RequestParam Estado estado,
                                                    @RequestParam String cidade) throws ValidationException {
        Cliente cliente = new Cliente();
        cliente.setNomeTutor(nomeTutor);
        cliente.setSexoTutor(sexoTutor);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setCep(cep);
        cliente.setEndereco(rua, complemento, numero);
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
}
