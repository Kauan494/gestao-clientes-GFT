package com.desafio.GFT.mapper;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.entity.Cliente;

public class ClienteMapper {
    public static Cliente toEntity(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        cliente.setDataCadastro(clienteDTO.getDataCadastro());
        return cliente;
    }

    public static void updateEntity(ClienteDTO clienteDTO, Cliente cliente){
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
    }

    public static ClienteDTO toDTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefone(cliente.getTelefone());
        clienteDTO.setEndereco(cliente.getEndereco());
        clienteDTO.setDataNascimento(cliente.getDataNascimento());
        clienteDTO.setDataCadastro(cliente.getDataCadastro());
        return clienteDTO;
    }
}
