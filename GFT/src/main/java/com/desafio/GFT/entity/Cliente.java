package com.desafio.GFT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name= "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Será gerado automaticamente
    private long id;

    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String endereco, String telefone, String email, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;

    }

}

