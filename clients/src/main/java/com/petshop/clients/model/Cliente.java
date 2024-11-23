package com.petshop.clients.model;

import com.petshop.clients.validation.ClienteValidation;
import jakarta.persistence.*;
import jakarta.xml.bind.ValidationException;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeTutor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexoTutor;

    @Column(nullable = false, unique = true)
    String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false, name = "endereco")
    private String endereco;

    @Transient
    private String rua;


    @Transient
    @Column(nullable = true)
    private String complemento;

    @Transient
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(nullable = false)
    private String cidade;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pets> pets;

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

    public void setCpf(String cpf) throws ValidationException {
        ClienteValidation.validateCpf(cpf);
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

    public void setEndereco(String rua, String numero) {
        this.rua = rua;
        this.numero = numero;
        this.endereco = String.format("%s, %s", rua, numero);
    }

    public void setEndereco(String rua, String complemento, String numero) {
        this.rua = rua;
        this.complemento = complemento;
        this.numero = numero;
        this.endereco = String.format("%s, %s, %s", rua, complemento, numero);
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) throws ValidationException {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Pets> getPets() {
        return pets;
    }

    public void setPets(List<Pets> pets) {
        this.pets = pets;
    }
}
