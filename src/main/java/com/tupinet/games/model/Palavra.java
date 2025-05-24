package com.tupinet.games.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "palavra")
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "palavra_id")
    private Integer id;

    @Column(name = "texto", length = 100, nullable = false)
    private String texto;

    @OneToMany(mappedBy = "palavra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TraducaoPalavra> traducoes = new HashSet<>();

    @OneToMany(mappedBy = "palavra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JogoPalavra> jogos = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Set<TraducaoPalavra> getTraducoes() {
        return traducoes;
    }

    public void setTraducoes(Set<TraducaoPalavra> traducoes) {
        this.traducoes = traducoes;
    }

    public Set<JogoPalavra> getJogos() {
        return jogos;
    }

    public void setJogos(Set<JogoPalavra> jogos) {
        this.jogos = jogos;
    }
}