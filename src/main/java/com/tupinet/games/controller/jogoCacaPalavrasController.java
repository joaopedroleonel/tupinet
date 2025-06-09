package com.tupinet.games.controller;

import com.tupinet.games.DTO.PosicaoPalavraDTO;
import com.tupinet.games.service.CacaPalavrasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class jogoCacaPalavrasController {

    @Autowired
    private CacaPalavrasService cacaPalavrasService;

    @GetMapping("/jogocacapalavras")
    public String cacaPalavras() {
        return "jogoCacaPalavras.html";
    }

    @GetMapping("/apicacapalavras")
    public ResponseEntity<PosicaoPalavraDTO> test() {
        PosicaoPalavraDTO caca = cacaPalavrasService.getPalavraAleatoria();
        return new ResponseEntity<>(caca, HttpStatus.OK);
    }
}
