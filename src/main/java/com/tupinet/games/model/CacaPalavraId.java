package com.tupinet.games.model;

import java.io.Serializable;
import java.util.Objects;

public class CacaPalavraId implements Serializable {

    private Integer palavra;
    private Integer jogo;

    public CacaPalavraId() {}

    public CacaPalavraId(Integer palavra, Integer jogo) {
        this.palavra = palavra;
        this.jogo = jogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacaPalavraId)) return false;
        CacaPalavraId that = (CacaPalavraId) o;
        return Objects.equals(palavra, that.palavra) &&
                Objects.equals(jogo, that.jogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(palavra, jogo);
    }

    // Getters e setters (opcional)
}
