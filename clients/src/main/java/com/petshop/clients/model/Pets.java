package com.petshop.clients.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pets {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomePet;

    @Column(nullable = false)
    private String tipoPet;

    @Column(nullable = false)
    private String racaPet;

    @Column(nullable = false)
    private String corPet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexoPet;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public String getTipoPet() {
        return tipoPet;
    }

    public void setTipoPet(String tipoPet) {
        this.tipoPet = tipoPet;
    }

    public String getRacaPet() {
        return racaPet;
    }

    public void setRacaPet(String racaPet) {
        this.racaPet = racaPet;
    }

    public String getCorPet() {
        return corPet;
    }

    public void setCorPet(String corPet) {
        this.corPet = corPet;
    }

    public Sexo getSexoPet() {
        return sexoPet;
    }

    public void setSexoPet(Sexo sexoPet) {
        this.sexoPet = sexoPet;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
