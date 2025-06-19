package com.tupinet.games.DTO;

public class PalavraComTraducaoDTO {
    private Integer id;
    private String texto;
    private String traducao;

    public PalavraComTraducaoDTO() {
    }

    public PalavraComTraducaoDTO(Integer id, String texto, String traducao) {
        this.id = id;
        this.texto = texto;
        this.traducao = traducao;
    }

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

    public String getTraducao() {
        return traducao;
    }

    public void setTraducao(String traducao) {
        this.traducao = traducao;
    }
}
