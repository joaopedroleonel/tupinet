package com.tupinet.games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "caca_palavra")
@IdClass(CacaPalavraId.class)
public class CacaPalavra {

    @Id
    @ManyToOne
    @JoinColumn(name = "palavra_id")
    private Palavra palavra;

    @Id
    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    private String posicao;

    // Getters e setters
}
