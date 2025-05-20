package com.tupinet.games.model;

import java.io.Serializable;
import java.util.Objects;

public class TraduzirPalavraId implements Serializable {

    private Integer palavra;
    private String traducao;

    public TraduzirPalavraId() {}

    public TraduzirPalavraId(Integer palavra, String traducao) {
        this.palavra = palavra;
        this.traducao = traducao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TraduzirPalavraId)) return false;
        TraduzirPalavraId that = (TraduzirPalavraId) o;
        return Objects.equals(palavra, that.palavra) &&
                Objects.equals(traducao, that.traducao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(palavra, traducao);
    }
}
