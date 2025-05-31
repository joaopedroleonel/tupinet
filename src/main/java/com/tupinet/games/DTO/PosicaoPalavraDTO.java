package com.tupinet.games.DTO;

public class PosicaoPalavraDTO {

    private String posicao;
    private String palavra;

    public PosicaoPalavraDTO() {
    }

    public PosicaoPalavraDTO(String posicao, String palavra) {
        this.posicao = posicao;
        this.palavra = palavra;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
