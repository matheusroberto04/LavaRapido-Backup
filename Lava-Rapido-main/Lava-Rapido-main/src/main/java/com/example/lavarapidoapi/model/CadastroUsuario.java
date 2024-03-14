package com.example.lavarapidoapi.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class CadastroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String nome_completo;
    private String email;
    private String senha;

}
