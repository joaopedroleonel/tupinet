package com.tupinet.games.DTO;

import java.util.Set;

public class SalaSolicitacaoDTO {

    private String nome;
    private Boolean ativo;
    private Set<Integer> jogosIds;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Set<Integer> getJogosIds() { return jogosIds; }
    public void setJogosIds(Set<Integer> jogosIds) { this.jogosIds = jogosIds; }

}
