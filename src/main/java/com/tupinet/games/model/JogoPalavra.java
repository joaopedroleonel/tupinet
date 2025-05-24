package com.tupinet.games.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "jogo_palavra")
@IdClass(JogoPalavra.JogoPalavraId.class)
public class JogoPalavra {

    @Id
    @Column(name = "jogo_id")
    private Integer jogoId;

    @Id
    @Column(name = "palavra_id")
    private Integer palavraId;

    @Column(name = "posicao", length = 50)
    private String posicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id", insertable = false, updatable = false)
    private Jogo jogo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "palavra_id", insertable = false, updatable = false)
    private Palavra palavra;

    public static class JogoPalavraId implements Serializable {
        private Integer jogoId;
        private Integer palavraId;

        public JogoPalavraId(){}

        public JogoPalavraId(Integer jogoId, Integer palavraId) {
            this.jogoId = jogoId;
            this.palavraId = palavraId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JogoPalavraId that = (JogoPalavraId) o;
            return Objects.equals(jogoId, that.jogoId) && Objects.equals(palavraId, that.palavraId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(jogoId, palavraId);
        }

        public Integer getJogoId() {
            return jogoId;
        }

        public void setJogoId(Integer jogoId) {
            this.jogoId = jogoId;
        }

        public Integer getPalavraId() {
            return palavraId;
        }

        public void setPalavraId(Integer palavraId) {
            this.palavraId = palavraId;
        }
    }

    public Integer getJogoId() {
        return jogoId;
    }

    public void setJogoId(Integer jogoId) {
        this.jogoId = jogoId;
    }

    public Integer getPalavraId() {
        return palavraId;
    }

    public void setPalavraId(Integer palavraId) {
        this.palavraId = palavraId;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }
}
