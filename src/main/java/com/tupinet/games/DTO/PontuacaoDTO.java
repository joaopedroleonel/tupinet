package com.tupinet.games.DTO;

public class PontuacaoDTO {
    private Integer jogoId;
    private String salaCod;
    private String aluno;
    private Integer pontos;
    private Integer acertos;

    public PontuacaoDTO(Integer jogoId, String salaCod, String aluno, Integer pontos, Integer acertos) {
        this.jogoId = jogoId;
        this.salaCod = salaCod;
        this.aluno = aluno;
        this.pontos = pontos;
        this.acertos = acertos;
    }

    public Integer getJogoId() {
        return jogoId;
    }

    public void setJogoId(Integer jogoId) {
        this.jogoId = jogoId;
    }

    public String getSalaCod() {
        return salaCod;
    }

    public void setSalaCod(String salaCod) {
        this.salaCod = salaCod;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }
}