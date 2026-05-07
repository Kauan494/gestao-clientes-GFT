package com.desafio.GFT.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ClienteDTO {
    @NotBlank(message = "Nome é obrigatório!")//Impede valor nulo
    private String nome;

    @NotBlank(message = "CPF é obrigatório!")
    @Size(min = 11, max = 11, message = "CPF deve conter apenas números e 11 digitos!")
    private String cpf;

    @NotBlank(message = "Email é obrigatório!")
    @Email(message = "Formato de e-mail inválido!")
    private String email;
    
    private Long id;
    private String telefone;
    private LocalDate dataNascimento;
    private String endereco;
    private LocalDateTime dataCadastro;
}
