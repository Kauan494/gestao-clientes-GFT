package com.desafio.GFT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;


}
