package com.tupinet.games.model;

import java.util.Set;

public class Palavreca {
    private String completa;
    private String comLacunas;
    private Set<Integer> indicesFaltando;
    private String traducaoTupi;

    public Palavreca(String completa, String comLacunas, Set<Integer> indicesFaltando, String traducaoTupi) {
        this.completa = completa;
        this.comLacunas = comLacunas;
        this.indicesFaltando = indicesFaltando;
        this.traducaoTupi = traducaoTupi;
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
    public String getTraducaoTupi() {
        return traducaoTupi;
    }

    public void setTraducaoTupi(String traducaoTupi) {
        this.traducaoTupi = traducaoTupi;
    }
}
