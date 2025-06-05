package com.tupinet.games.DTO;

public class PontuacaoDTO {
    private Integer jogoId;
    private Integer salaId;
    private String aluno;
    private Integer pontos;
    private Integer acertos;

    public PontuacaoDTO(Integer jogoId, Integer salaId, String aluno, Integer pontos, Integer acertos) {
        this.jogoId = jogoId;
        this.salaId = salaId;
        this.aluno = aluno;
        this.pontos = pontos;
        this.acertos = acertos;
    }

    public Integer getJogoId() { return jogoId; }
    public void setJogoId(Integer jogoId) { this.jogoId = jogoId; }

    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }

    public String getAluno() { return aluno; }
    public void setAluno(String aluno) { this.aluno = aluno; }

    public Integer getPontos() { return pontos; }
    public void setPontos(Integer pontos) { this.pontos = pontos; }

    public Integer getAcertos() { return acertos; }
    public void setAcertos(Integer acertos) { this.acertos = acertos; }
}