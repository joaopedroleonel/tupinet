package com.tupinet.games.model;

import java.io.Serializable;
import java.util.Objects;

public class CompletarPalavraId implements Serializable {

    private Integer palavra;
    private Integer jogo;

    public CompletarPalavraId() {}

    public CompletarPalavraId(Integer palavra, Integer jogo) {
        this.palavra = palavra;
        this.jogo = jogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompletarPalavraId)) return false;
        CompletarPalavraId that = (CompletarPalavraId) o;
        return Objects.equals(palavra, that.palavra) &&
                Objects.equals(jogo, that.jogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(palavra, jogo);
    }
}
