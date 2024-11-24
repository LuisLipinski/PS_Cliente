package com.petshop.clients.model;

public enum SexoPet {
    MACHO("Macho"),
    FEMEA("Femea");

    private String sexoPet;

    SexoPet(String sexoPet) {
        this.sexoPet = sexoPet;
    }

    public String getSexoPet() {
        return sexoPet;
    }

}
