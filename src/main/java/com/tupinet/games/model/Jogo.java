package com.tupinet.games.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_jogo_id", nullable = false)
    private TipoJogo tipoJogo;

    @JsonIgnore
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JogoPalavra> palavras = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SalaJogo> salaJogos = new HashSet<>();

    @Column(name = "rota", length = 100, nullable = false)
    private String rota;

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

    public Set<SalaJogo> getSalaJogos() {
        return salaJogos;
    }

    public void setSalaJogos(Set<SalaJogo> salaJogos) {
        this.salaJogos = salaJogos;
    }

    public String getRota() { return rota; }

    public void setRota(String rota) { this.rota = rota; }
}