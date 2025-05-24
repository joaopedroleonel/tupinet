package com.tupinet.games.repository;

import com.tupinet.games.model.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalavraRepository extends JpaRepository<Palavra, Integer> {}
