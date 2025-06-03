package com.tupinet.games.DTO;
import java.util.Set;

public class SalaRespostaDTO {

    private Integer id;
    private String nome;
    private String codigo;
    private Boolean ativo;
    private Set<Integer> jogosIds;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Set<Integer> getJogosIds() { return jogosIds; }
    public void setJogosIds(Set<Integer> jogosIds) { this.jogosIds = jogosIds; }

}
