package com.desafio.GFT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;

    }

    @OneToMany(mappedBy = "cliente")//Significa que esse atributo cliente esta dentro da classe endereco
    private List<Endereco> enderecos;

}

