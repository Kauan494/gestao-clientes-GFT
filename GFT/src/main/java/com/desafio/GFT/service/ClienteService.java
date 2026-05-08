package com.desafio.GFT.service;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.entity.Cliente;
import com.desafio.GFT.exception.RecursoNaoEncontradoException;
import com.desafio.GFT.mapper.ClienteMapper;
import com.desafio.GFT.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//Onde fica as regras de négocio, decide oq fazer
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;//É a interface que tem os comandos de banco

    public ClienteService(ClienteRepository clienteRepository) {
        //Injeta (entrega) automaticamente o repository dentro do service através desse construtor.
        this.clienteRepository = clienteRepository;
    }

    //listar clientes com paginação
    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteMapper::toDTO);
    }

    public Cliente buscarPorId(Long id) {
        //buscar por um cliente pelo id
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com ID "+id+" não encontrado!"));
    }

    public List<ClienteDTO> buscarPorNome(String nome) {
        //buscar pelo nome do cliente
        return clienteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ClienteMapper :: toDTO)
                .toList();
    }

    public Optional<ClienteDTO> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(ClienteMapper::toDTO);
    }

    public Cliente salvarClienteDTO(ClienteDTO clienteDTO) {
        if(clienteRepository.findByCpf(clienteDTO.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }
        if(clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }

        //Converter o DTO para Entity
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);


        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com ID "+id+" não encontrado!"));

        if (clienteDTO.getCpf() != null && !cliente.getCpf().equals(clienteDTO.getCpf())) {
            throw new RuntimeException("CPF não pode ser alterado.");
        }

        Optional<Cliente> emailExistente = clienteRepository.findByEmail(clienteDTO.getEmail());
        if(emailExistente.isPresent() && emailExistente.get().getId() != id){
            throw new RuntimeException("Email já está em uso.");
        }

        //Converter o DTO para Entity
        ClienteMapper.updateEntity(clienteDTO, cliente);

        return  clienteRepository.save(cliente);

    }


    public void removerCliente(Long id) {
        //Excluir cliente
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente com ID "+id+" não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
