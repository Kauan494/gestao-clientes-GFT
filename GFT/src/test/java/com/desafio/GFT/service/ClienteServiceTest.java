package com.desafio.GFT.service;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.entity.Cliente;
import com.desafio.GFT.exception.RecursoNaoEncontradoException;
import com.desafio.GFT.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    //Eles criam um repository fake e um service usando esse fake
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @Test
    @DisplayName("Salvar cliente quando estiver tudo ok")
    void salvarClienteDTOSuccess() {
        //Simular banco
        when(clienteRepository.findByCpf(cliente.getCpf()))//Cpf não existe
                .thenReturn(Optional.empty());

        when(clienteRepository.findByEmail(cliente.getEmail()))//Email não existe
                .thenReturn(Optional.empty());

        when(clienteRepository.save(any()))//Salva e retorna o cliente
                .thenReturn(cliente);

        Cliente resultado = clienteService.salvarClienteDTO(clienteDTO);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNome());
    }

    @Test
    @DisplayName("Retorna erro quando nome estiver vazio")
    void salvarClienteDTONomeVazio() {
        clienteDTO.setNome(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.salvarClienteDTO(clienteDTO));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Retorna erro quando CPF estiver vazio ou nulo")
    void salvarClienteDTOCpfVazio() {
        clienteDTO.setCpf(""); 

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.salvarClienteDTO(clienteDTO));
    
        assertEquals("CPF é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Retorna erro quando Cpf estiver inválido")
    void salvarClienteDTOCpfinvalido() {
        clienteDTO.setCpf("123");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.salvarClienteDTO(clienteDTO));
        assertEquals("CPF inválido", exception.getMessage());
    }
    
    @Test
    @DisplayName("Retorna erro quando CPF já cadstrado")
    void salvarClienteDTOCpfduplicado(){
        //é para mostrar q já existe o moesmo registro no banco
        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> clienteService.salvarClienteDTO(clienteDTO));
        assertEquals("CPF já cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Retorna erro quando email estiver vazio")
    void salvarClienteDTOEmailvazio(){
        clienteDTO.setEmail("");

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> clienteService.salvarClienteDTO(clienteDTO));
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Retorna erro quando formato do email for inválido")
    void salvarClienteDTOEmailFormatoInvalido() {
        clienteDTO.setEmail("pedrogmail"); 

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clienteService.salvarClienteDTO(clienteDTO));
    
        assertEquals("Formato de e-mail inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Retorna erro quando email já cadastrado")
    void salvarClienteDTOEmailduplicado(){
        when(clienteRepository.findByCpf(clienteDTO.getCpf())).thenReturn(Optional.empty());//deixa o cpf passar para testar dó o erro do email
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.of(cliente));

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> clienteService.salvarClienteDTO(clienteDTO));
        assertEquals("Email já cadastrado",exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar cliente com sucesso")
    void atualizarClienteSuccess() {
        long id = 1L;

        //Simula que encontrou um cliente
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        //Email não está duplicado
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.empty());

        //Simula save
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.atualizarCliente(id,clienteDTO);

        assertNotNull(resultado);//Verifica se o retorno não é nulo
        assertEquals("Pedro", resultado.getNome());
    }

    @Test
    @DisplayName("Erro ao atualizar cliente inexistente")
    void atualizarClienteInexistente() {
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class,
                ()-> clienteService.atualizarCliente(id,clienteDTO));
    }

    @Test
    @DisplayName("Erro ao tentar alterar CPF")
    void atualizarClienteCpfAlterado(){
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        clienteDTO.setCpf("99988877766");

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> clienteService.atualizarCliente(id,clienteDTO));

        assertEquals("CPF não pode ser alterado.", exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao atualizar com email já em uso")
    void atualizarClienteEmailDuplicado(){
        Long id = 1L;
        Cliente novoCliente = new Cliente();
        novoCliente.setId(2L);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.of(novoCliente));

        RuntimeException exception = assertThrows(RuntimeException.class,//Serve para testar se o metodo lança o erro corretamente
                ()-> clienteService.atualizarCliente(id,clienteDTO));

        assertEquals("Email já está em uso.",exception.getMessage());

    }

    @Test
    @DisplayName("Deletar cliente com sucesso")
    void removerClienteSuccess() {
        Long id = 1L;

        when(clienteRepository.existsById(id)).thenReturn(true);

        clienteService.removerCliente(id);

        //Verifica se o delete foi chamado
        verify(clienteRepository).deleteById(id);
    }

    @Test
    @DisplayName("Erro ao remover cliente inexistente")
    void removerClienteInexistente() {
        Long id = 1L;

        when(clienteRepository.existsById(id)).thenReturn(false);
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class,
                ()-> clienteService.removerCliente(id));

        assertEquals("Cliente com ID 1 não encontrado", exception.getMessage());
    }

    @BeforeEach
    void setup() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Pedro");
        clienteDTO.setCpf("11122233344");
        clienteDTO.setEmail("teste@email.com");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Pedro");
        cliente.setCpf("11122233344");
        cliente.setEmail("teste@email.com");
    }
}