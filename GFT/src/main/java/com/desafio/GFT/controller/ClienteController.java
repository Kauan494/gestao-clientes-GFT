package com.desafio.GFT.controller;

import com.desafio.GFT.dto.ClienteDTO;
import com.desafio.GFT.entity.Cliente;
import com.desafio.GFT.mapper.ClienteMapper;
import com.desafio.GFT.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Onde fica os endpoints
@RestController//Para trabalhar com API REST
@RequestMapping("/api/clientes")//Porta de entrada do controller
@RequiredArgsConstructor //Ele gera um construtor com todos os atributos que são: final ou anotados como @NonNull
public class ClienteController {

    private final ClienteService clienteService;


    @GetMapping//listar todos os clientes usando paginação
    public ResponseEntity<?> listarClientes(
            //Os Param servem para as variaveis virarem parametros de pesquisa
            //Transforma valores da URL em parametros 
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @ParameterObject Pageable pageable){

        if (nome != null) {
            return ResponseEntity.ok(clienteService.buscarPorNome(nome));
        }

        if (cpf != null) {
            return ResponseEntity.ok(clienteService.buscarPorCpf(cpf)
                    .map(List::of)
                    .orElse(List.of())
            );
        }

        return ResponseEntity.ok(clienteService.listarClientes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscarClienteDTO(id));
    }
    //DTO não tem cliente dentro de endereco
    // Loop infinito eliminado
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = clienteService.salvarClienteDTO(clienteDTO);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO clienteDTO){
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(ClienteMapper.toDTO(clienteAtualizado));
    }


    @DeleteMapping("/{id}")//Deletar o cliente
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
