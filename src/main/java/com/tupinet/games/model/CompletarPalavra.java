package com.tupinet.games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "completar_palavra")
@IdClass(CompletarPalavraId.class)
public class CompletarPalavra {

    @Id
    @ManyToOne
    @JoinColumn(name = "palavra_id")
    private Palavra palavra;

    @Id
    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    // Getters e setters
}
