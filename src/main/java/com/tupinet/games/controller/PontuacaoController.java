package com.tupinet.games.controller;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.service.PontuacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PontuacaoController {

    @Autowired
    private PontuacaoService pontuacaoService;

    @PutMapping("/salvar-pontuacao")
    public ResponseEntity<Void> salvar(@RequestBody PontuacaoDTO pontuacao) {
        try {
            pontuacaoService.setPontuacao(pontuacao);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
