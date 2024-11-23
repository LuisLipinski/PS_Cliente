package com.petshop.clients.model;

public enum SortField {
    NOME_TUTOR("nomeTutor"),
    ID("id"),
    CPF("cpf");

    private String field;

    SortField(String field){
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
