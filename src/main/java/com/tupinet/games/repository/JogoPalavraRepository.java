package com.tupinet.games.repository;

import com.tupinet.games.model.JogoPalavra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoPalavraRepository extends JpaRepository<JogoPalavra, JogoPalavra.JogoPalavraId> {}