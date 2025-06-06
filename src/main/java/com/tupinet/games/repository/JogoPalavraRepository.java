package com.tupinet.games.repository;

import com.tupinet.games.DTO.PosicaoPalavraDTO;
import com.tupinet.games.model.JogoPalavra;
import com.tupinet.games.model.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JogoPalavraRepository extends JpaRepository<JogoPalavra, JogoPalavra.JogoPalavraId> {
    @Query("SELECT p FROM JogoPalavra jp JOIN jp.palavra p WHERE jp.jogoId = :jogoId")
    List<Palavra> findPalavrasByJogoId(@Param("jogoId") Integer jogoId);

    @Query("SELECT new com.tupinet.games.DTO.PosicaoPalavraDTO(jp.posicao, p.texto) FROM JogoPalavra jp INNER JOIN jp.palavra p WHERE jp.jogoId = :jogoId")
    List<PosicaoPalavraDTO> findPalavrasEPosicaoByJogoId(@Param("jogoId") Integer jogoId);
}