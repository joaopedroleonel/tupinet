package com.tupinet.games.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Integer id;

    private String nome;
    private String codigo;
    private Boolean ativo;

    @ManyToMany(mappedBy = "salas")
    private Set<Professor> professores;

    @ManyToMany
    @JoinTable(
            name = "sala_jogo",
            joinColumns = @JoinColumn(name = "sala_id"),
            inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private Set<Jogo> jogos;

    // Getters e setters
}

