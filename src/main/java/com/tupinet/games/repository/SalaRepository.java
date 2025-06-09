package com.tupinet.games.repository;

import com.tupinet.games.model.Sala;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
