package com.tupinet.games.DTO;

import com.tupinet.games.model.Jogo;

import java.util.List;
import java.util.Set;

public class SalaApiDTO {
    private Integer id;
    private String nome;
    private String codigo;
    private Boolean ativo;
    private List<Jogo> jogos;

    public SalaApiDTO() {
    }

    public SalaApiDTO(Integer id, String nome, String codigo, Boolean ativo, List<Jogo> jogos) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.ativo = ativo;
        this.jogos = jogos;
    }

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}
