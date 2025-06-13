package com.tupinet.games.DTO;

public class RankingDTO {

    private String aluno;
    private Integer acertos;
    private Integer pontos;
    private String nome;

    public RankingDTO(String aluno, Integer acertos, Integer pontos, String nome) {
        this.aluno = aluno;
        this.acertos = acertos;
        this.pontos = pontos;
        this.nome = nome;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
