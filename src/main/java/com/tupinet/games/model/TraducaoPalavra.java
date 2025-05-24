package com.tupinet.games.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "traducao_palavra")
@IdClass(TraducaoPalavra.TraducaoPalavraId.class)
public class TraducaoPalavra {

    @Id
    @Column(name = "palavra_id")
    private Integer palavraId;

    @Id
    @Column(name = "traducao", length = 100)
    private String traducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "palavra_id", insertable = false, updatable = false)
    private Palavra palavra;

    public static class TraducaoPalavraId implements Serializable {
        private Integer palavraId;
        private String traducao;

        public TraducaoPalavraId(){}

        public TraducaoPalavraId(Integer palavraId, String traducao) {
            this.palavraId = palavraId;
            this.traducao = traducao;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TraducaoPalavraId that = (TraducaoPalavraId) o;
            return Objects.equals(palavraId, that.palavraId) && Objects.equals(traducao, that.traducao);
        }

        @Override
        public int hashCode() {
            return Objects.hash(palavraId, traducao);
        }

        public Integer getPalavraId() {
            return palavraId;
        }

        public void setPalavraId(Integer palavraId) {
            this.palavraId = palavraId;
        }

        public String getTraducao() {
            return traducao;
        }

        public void setTraducao(String traducao) {
            this.traducao = traducao;
        }
    }

    public Integer getPalavraId() {
        return palavraId;
    }

    public void setPalavraId(Integer palavraId) {
        this.palavraId = palavraId;
    }

    public String getTraducao() {
        return traducao;
    }

    public void setTraducao(String traducao) {
        this.traducao = traducao;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }
}