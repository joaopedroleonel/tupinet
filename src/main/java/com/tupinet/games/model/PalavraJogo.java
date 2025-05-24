package com.tupinet.games.model;

import java.util.Set;

public class PalavraJogo {
    private String completa;
    private String comLacunas;
    private Set<Integer> indicesFaltando;

    public PalavraJogo(String completa, String comLacunas, Set<Integer> indicesFaltando) {
        this.completa = completa;
        this.comLacunas = comLacunas;
        this.indicesFaltando = indicesFaltando;
    }

    public String getCompleta() {
        return completa;
    }

    public String getComLacunas() {
        return comLacunas;
    }

    public Set<Integer> getIndicesFaltando() {
        return indicesFaltando;
    }
}
