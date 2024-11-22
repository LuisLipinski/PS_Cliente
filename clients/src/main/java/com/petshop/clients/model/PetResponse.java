package com.petshop.clients.model;

public class PetResponse {
    private Long id;
    private String nomePet;
    private String tipoPet;
    private String racaPet;
    private String corPet;
    private Sexo sexoPet;

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
}
