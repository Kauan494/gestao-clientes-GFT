package com.desafio.GFT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor //cria construtor vazio
@AllArgsConstructor//cria construtor com parametros

//Esse lado manda no relacionamento
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Será gerado automaticamente
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    @ManyToOne//Muitos para um
    @JoinColumn(name = "cliente_id")//Cria essa FK
    private Cliente cliente;
}
