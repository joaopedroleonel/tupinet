package com.tupinet.games.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pontuacao")
@IdClass(PontuacaoId.class)
public class Pontuacao {

    @Id
    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @Id
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @Id
    private String aluno;

    private Integer pontos;

    private LocalDate data;

    // Getters e setters
}

