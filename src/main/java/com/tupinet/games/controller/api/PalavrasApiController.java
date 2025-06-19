package com.tupinet.games.controller.api;

import com.tupinet.games.DTO.PalavraComTraducaoDTO;
import com.tupinet.games.model.Palavra;
import com.tupinet.games.service.PalavrasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/palavras")
public class PalavrasApiController {

    @Autowired
    private PalavrasService palavrasService;

    @GetMapping
    public List<PalavraComTraducaoDTO> listarPalavras(){
        return palavrasService.listarPalavrasComTraducoes();
    }

}
