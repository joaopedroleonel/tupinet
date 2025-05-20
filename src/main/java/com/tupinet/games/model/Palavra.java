package com.tupinet.games.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "palavra")
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "palavra_id")
    private Integer id;

    private String texto;

    @OneToMany(mappedBy = "palavra")
    private Set<CacaPalavra> cacaPalavras;

    @OneToMany(mappedBy = "palavra")
    private Set<CompletarPalavra> completarPalavras;

    @OneToMany(mappedBy = "palavra")
    private Set<TraduzirPalavra> traduzirPalavras;

    // Getters e setters
}
