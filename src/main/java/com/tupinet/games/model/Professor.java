package com.tupinet.games.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Integer id;

    private String nome;
    private String email;
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "sala_professor",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "sala_id")
    )
    private Set<Sala> salas;

    // Getters e setters
}

