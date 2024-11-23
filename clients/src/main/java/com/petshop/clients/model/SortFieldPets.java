package com.petshop.clients.model;

public enum SortFieldPets {
    NOME_PET("nomePet"),
    ID("id"),
    TIPO_PET("tipoPet"),
    RACA_PET("racaPet");

    private String field;

    SortFieldPets(String field){
        this.field = field;
    }

    public String getFieldPets() {
        return field;
    }
}
