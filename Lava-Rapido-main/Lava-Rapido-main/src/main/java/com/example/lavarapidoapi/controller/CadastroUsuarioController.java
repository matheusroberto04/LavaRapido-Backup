package com.example.lavarapidoapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.lavarapidoapi.model.CadastroUsuario;
import com.example.lavarapidoapi.repository.CadastroUsuarioRepository;

@RestController
@RequestMapping("cadastrousuario")
public class CadastroUsuarioController{
    //Criacao de uma lista para armazenar dados de cadastro!

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CadastroUsuarioRepository cadastroUsuarioRepository;

    //Endpoint que retorna os cadastros existentes
    @GetMapping
    public List<CadastroUsuario> index(){
        return cadastroUsuarioRepository.findAll();
    }
    //Endpoint para criacao de um novo cadastro   
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CadastroUsuario create(@RequestBody CadastroUsuario cadastroUsuario){
        log.info("Cadastrando usuario: {}", cadastroUsuario);
        cadastroUsuarioRepository.save(cadastroUsuario);
        return cadastroUsuario;
    }
    @GetMapping("{id}")
    public ResponseEntity<CadastroUsuario> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        
        return cadastroUsuarioRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //Exclui um cadastro pelo ID
    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando cadastro {}", id);

        verificarSeExisteCadastro(id);

        cadastroUsuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
    //Atualiza um cadastro existente
    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CadastroUsuario cadastro) {
        log.info("Atualizando cadastro pelo id {} para {}", id, cadastro);
        
        verificarSeExisteCadastro(id);

        cadastro.setId(id);
        cadastroUsuarioRepository.save(cadastro);
        return ResponseEntity.ok(cadastro);
    }
    //Metodo privado para que se busque um cadastro pelo ID, sendo utilizado somente por funcionarios do lava-rapido
    private void verificarSeExisteCadastro(Long id) {
        cadastroUsuarioRepository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Cadastro nao encontrado" )
        );
}

}