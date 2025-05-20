package com.tupinet.games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "traduzir_palavra")
@IdClass(TraduzirPalavraId.class)
public class TraduzirPalavra {

    @Id
    @ManyToOne
    @JoinColumn(name = "palavra_id")
    private Palavra palavra;

    @Id
    private String traducao;

    // Getters e setters
}
