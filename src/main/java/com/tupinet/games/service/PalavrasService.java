package com.tupinet.games.service;

import com.tupinet.games.DTO.PalavraComTraducaoDTO;
import com.tupinet.games.model.Palavra;
import com.tupinet.games.repository.PalavraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalavrasService {

    @Autowired
    private PalavraRepository palavraRepository;

    public List<PalavraComTraducaoDTO> listarPalavrasComTraducoes() {
        return palavraRepository.findPalavrasComTraducoes();
    }

}
