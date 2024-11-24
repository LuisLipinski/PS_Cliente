package com.petshop.clients.validation;

public class NomeValidation {

    public boolean isvalidNomeTutor(String nomeTutor) {
        if (nomeTutor == null || nomeTutor.trim().isEmpty()) {
            return false; // Nome não pode ser nulo ou vazio
        }

        String[] partes = nomeTutor.trim().split("\\s+"); // Divide o nome em palavras (usando espaço como delimitador)
        if (partes.length < 2) {
            return false; // Deve conter pelo menos 2 palavras
        }

        // Verifica se cada palavra tem no mínimo 3 caracteres
        for (String parte : partes) {
            if (parte.length() < 3) {
                return false; // Se alguma palavra tem menos de 3 caracteres, retorna falso
            }
        }

        return true; // Nome válido
    }

    public boolean isvalidNomePet(String nomePet) {
        if(nomePet == null || nomePet.trim().isEmpty()) {
            return false;
        }

        if(nomePet.length() < 3) {
            return false;
        }

        return true;
    }
}
