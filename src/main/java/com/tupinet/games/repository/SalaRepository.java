package com.tupinet.games.repository;

import com.tupinet.games.model.Sala;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    boolean existsByCodigo(String codigo);
    Optional<Sala> findByCodigo(String codigo);

    @Override
    @EntityGraph(attributePaths = {"salaJogos", "salaJogos.jogo"})
    List<Sala> findAll();

    @EntityGraph(attributePaths = {"salaJogos", "salaJogos.jogo"})
    Optional<Sala> findWithSalaJogosByCodigo(String codigo);

    @Query("SELECT s FROM Sala s LEFT JOIN FETCH s.salaJogos sj LEFT JOIN FETCH sj.jogo")
    List<Sala> findAllComJogos();

    @Query("SELECT s FROM Sala s LEFT JOIN FETCH s.salaJogos sj LEFT JOIN FETCH sj.jogo WHERE s.id = :id")
    Optional<Sala> findByIdComJogos(@Param("id") Integer id);
}
