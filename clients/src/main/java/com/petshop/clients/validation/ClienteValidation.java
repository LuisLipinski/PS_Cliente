package com.petshop.clients.validation;

import jakarta.xml.bind.ValidationException;

public class ClienteValidation {

    public static void validateCpf(String cpf) throws ValidationException {
        if(cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new ValidationException("CPF deve conter 11 dígitos numéricos.");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new ValidationException("CPF inválido.");
        }

        int digito1 = calcularDigito(cpf, 10);
        int digito2 = calcularDigito(cpf, 11);

        if (cpf.charAt(9) - '0' != digito1 || cpf.charAt(10) - '0' != digito2) {
            System.out.println("CPF: " + cpf);
            System.out.println("Dígito calculado 1: " + digito1);
            System.out.println("Dígito calculado 2: " + digito2);
            throw new ValidationException("CPF inválido");
        }
        System.out.println("CPF válido");
    }

    private static int calcularDigito(String cpf, int pesoInicial) {
        int soma =0;
        int peso = pesoInicial;
        for (int i = 0; i < pesoInicial -1; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int resto = soma % 11;
        return  resto < 2 ? 0 : 11 - resto;
    }
}
