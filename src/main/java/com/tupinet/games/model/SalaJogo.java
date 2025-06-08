package com.tupinet.games.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class SalaJogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @OneToMany(mappedBy = "salaJogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pontuacao> pontuacoes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Set<Pontuacao> getPontuacoes() {
        return pontuacoes;
    }

    public void setPontuacoes(Set<Pontuacao> pontuacoes) {
        this.pontuacoes = pontuacoes;
    }
}
