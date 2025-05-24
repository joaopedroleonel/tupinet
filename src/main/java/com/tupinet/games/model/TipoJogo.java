package com.tupinet.games.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_jogo")
public class TipoJogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_jogo_id")
    private Integer id;

    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "tipoJogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Jogo> jogos = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(Set<Jogo> jogos) {
        this.jogos = jogos;
    }
}