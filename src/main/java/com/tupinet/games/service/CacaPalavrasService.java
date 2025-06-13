package com.tupinet.games.service;

import com.tupinet.games.DTO.PosicaoPalavraDTO;
import com.tupinet.games.repository.JogoPalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CacaPalavrasService {

    @Autowired
    private JogoPalavraRepository jogoPalavraRepository;

    public PosicaoPalavraDTO getPalavraAleatoria(){

        List<PosicaoPalavraDTO> palavras = jogoPalavraRepository.findPalavrasEPosicaoByJogoId(3);
        PosicaoPalavraDTO palavra = palavras.get(new Random().nextInt(palavras.size()));

        return palavra;
    }

}