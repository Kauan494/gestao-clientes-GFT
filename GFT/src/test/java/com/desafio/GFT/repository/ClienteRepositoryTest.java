package com.desafio.GFT.repository;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.entity.Cliente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest//Indica q é uma classe de teste q vai testar um repository com JPA(banco em memória)
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Retorne com sucesso o cliente ao buscar pelo nome")
    void findByNomeContainingIgnoreCaseSuccess() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Rafaela");
        clienteDTO.setCpf("12345678910");
        clienteDTO.setEmail("teste@email.com");
        this.createCliente(clienteDTO);

        List<Cliente> resultado = this.clienteRepository.findByNomeContainingIgnoreCase("Rafaela");

        //Esse metodo confere o resultado final da execução do teste
        assertThat(resultado).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retorna vazio quando nome não existir")
    void findByNomeContainingIgnoreCaseNotFound() {
        List<Cliente> resultado = this.clienteRepository.findByNomeContainingIgnoreCase("xyz");//colocar algo que não existe no banco para ver se ira retorna vazio
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Retorne com sucesso o cpf do cliente")
    void findByCpfSuccess() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Rafaela");
        clienteDTO.setCpf("12345678910");
        clienteDTO.setEmail("teste@email.com");
        this.createCliente(clienteDTO);

        Optional<Cliente> resultado = this.clienteRepository.findByCpf("123456789101");
        assertThat(resultado).isPresent();
    }

    @Test
    @DisplayName("Deve retorna vazio quando o cpf não existir")
    void findByCpfNotFound() {
        Optional<Cliente> resultado = this.clienteRepository.findByCpf("123456789100");
        assertThat(resultado).isEmpty();//Signifa dizer q o Optional não tem valor

    }

    @Test
    @DisplayName("Retorne com sucesso o email do cliente")
    void findByEmailSuccess() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Rafaela");
        clienteDTO.setCpf("12345678910");
        clienteDTO.setEmail("teste@email.com");

        createCliente(clienteDTO);

        Optional <Cliente> resultado = this.clienteRepository.findByEmail("teste@email.com");
        assertThat(resultado).isPresent();
    }

    @Test
    @DisplayName("Deve retorna o email vazio")
    void findByEmailNotFound() {
        Optional<Cliente> resultado = this.clienteRepository.findByEmail("teste2@email.com");
        assertThat(resultado).isEmpty();
    }

    private Cliente createCliente(ClienteDTO clienteDTO) {
           Cliente cliente = new Cliente();
           cliente.setNome(clienteDTO.getNome());
           cliente.setCpf(clienteDTO.getCpf());
           cliente.setEmail(clienteDTO.getEmail());

           this.entityManager.persist(cliente);
           return cliente;
    }
}