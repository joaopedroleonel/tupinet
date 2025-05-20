package com.tupinet.games.model;

import java.io.Serializable;
import java.util.Objects;

public class PontuacaoId implements Serializable {

    private Integer jogo;
    private Integer sala;
    private String aluno;

    public PontuacaoId() {}

    public PontuacaoId(Integer jogo, Integer sala, String aluno) {
        this.jogo = jogo;
        this.sala = sala;
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PontuacaoId)) return false;
        PontuacaoId that = (PontuacaoId) o;
        return Objects.equals(jogo, that.jogo) &&
                Objects.equals(sala, that.sala) &&
                Objects.equals(aluno, that.aluno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jogo, sala, aluno);
    }
}
