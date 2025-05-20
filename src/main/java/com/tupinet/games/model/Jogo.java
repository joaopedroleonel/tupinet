package com.tupinet.games.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "jogo")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jogo_id")
    private Integer id;

    private String nome;
    private String imagem;

    @ManyToMany(mappedBy = "jogos")
    private Set<Sala> salas;

    @OneToMany(mappedBy = "jogo")
    private Set<CacaPalavra> cacaPalavras;

    @OneToMany(mappedBy = "jogo")
    private Set<CompletarPalavra> completarPalavras;

    @OneToMany(mappedBy = "jogo")
    private Set<Pontuacao> pontuacoes;

    // Getters e setters
}
