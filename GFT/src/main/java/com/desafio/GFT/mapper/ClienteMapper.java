package com.desafio.GFT.mapper;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.dto.EnderecoDTO;
import com.desafio.GFT.entity.Cliente;
import com.desafio.GFT.entity.Endereco;

import java.util.ArrayList;
import java.util.List;

public class ClienteMapper {
    public static Cliente toEntity(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        cliente.setDataCadastro(clienteDTO.getDataCadastro());

        if(clienteDTO.getEnderecos() != null){
            List<Endereco> enderecos = new ArrayList<>();
            for(EnderecoDTO enderecoDTO : clienteDTO.getEnderecos()){
                Endereco endereco = new Endereco();
                endereco.setCep(enderecoDTO.getCep());
                endereco.setLogradouro(enderecoDTO.getLogradouro());
                endereco.setComplemento(enderecoDTO.getComplemento());
                endereco.setNumero(enderecoDTO.getNumero());
                endereco.setBairro(enderecoDTO.getBairro());
                endereco.setCidade(enderecoDTO.getCidade());
                endereco.setEstado(enderecoDTO.getEstado());

                endereco.setCliente(cliente);
                enderecos.add(endereco);
            }
            cliente.setEnderecos(enderecos);
        }
        return cliente;
    }

    public static void updateEntity(ClienteDTO clienteDTO, Cliente cliente){
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
    }

    public static ClienteDTO toDTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefone(cliente.getTelefone());
        clienteDTO.setDataNascimento(cliente.getDataNascimento());
        clienteDTO.setDataCadastro(cliente.getDataCadastro());
        return clienteDTO;
    }
}
