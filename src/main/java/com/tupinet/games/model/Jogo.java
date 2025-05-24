package com.tupinet.games.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jogo")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jogo_id")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "imagem", length = 255)
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_jogo_id", nullable = false)
    private TipoJogo tipoJogo;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JogoPalavra> palavras = new HashSet<>();

    @ManyToMany(mappedBy = "jogos")
    private Set<Sala> salas = new HashSet<>();

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pontuacao> pontuacoes = new HashSet<>();

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public TipoJogo getTipoJogo() {
        return tipoJogo;
    }

    public void setTipoJogo(TipoJogo tipoJogo) {
        this.tipoJogo = tipoJogo;
    }

    public Set<JogoPalavra> getPalavras() {
        return palavras;
    }

    public void setPalavras(Set<JogoPalavra> palavras) {
        this.palavras = palavras;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    public Set<Pontuacao> getPontuacoes() {
        return pontuacoes;
    }

    public void setPontuacoes(Set<Pontuacao> pontuacoes) {
        this.pontuacoes = pontuacoes;
    }
}