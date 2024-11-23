package com.petshop.clients.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public class ClienteResponse {
    private Long id;
    private String nomeTutor;
    @Enumerated(EnumType.STRING)
    private Sexo sexoTutor;
    private String cpf;
    private String telefone;
    private String cep;
    private String endereco;
    private Estado estado;
    private String cidade;
    private List<PetResponse> pets;

    public ClienteResponse() {

    }

    public ClienteResponse(Long id, String nomeTutor, Sexo sexoTutor, String cpf, String telefone, String cep, String endereco, Estado estado, String cidade, List<PetResponse> pets) {
        this.id = id;
        this.nomeTutor = nomeTutor;
        this.sexoTutor = sexoTutor;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cep = cep;
        this.endereco = endereco;
        this.estado = estado;
        this.cidade = cidade;
        this.pets = pets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTutor() {
        return nomeTutor;
    }

    public void setNomeTutor(String nomeTutor) {
        this.nomeTutor = nomeTutor;
    }

    public Sexo getSexoTutor() {
        return sexoTutor;
    }

    public void setSexoTutor(Sexo sexoTutor) {
        this.sexoTutor = sexoTutor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<PetResponse> getPets() {
        return pets;
    }

    public void setPets(List<PetResponse> pets) {
        this.pets = pets;
    }
}
