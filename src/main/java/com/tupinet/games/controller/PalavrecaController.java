package com.tupinet.games.controller;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.model.Palavreca;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.service.PalavrecaService;
import com.tupinet.games.service.PontuacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
public class PalavrecaController {

    @Autowired
    private PalavrecaService palavraService;

    @Autowired
    private PontuacaoService pontuacaoService;

    private final Integer JOGO_ID = 2;
    private final String SALA_COD = "PORT123";
    private final String ALUNO = "Luan";

    @GetMapping("/palavreca")
    public String exibirJogo(Model model, HttpSession session) {
        iniciarSessaoSeNecessario(session);

        // Verifica se é um novo jogo ou continuação
        if (session.getAttribute("palavraAtual") == null) {
            Palavreca novaPalavra = palavraService.getPalavraAleatoria();
            session.setAttribute("palavraAtual", novaPalavra);
        }

        // Recupera dados da sessão
        Palavreca palavraAtual = (Palavreca) session.getAttribute("palavraAtual");
        Integer rodada = (Integer) session.getAttribute("rodada");
        Integer score = (Integer) session.getAttribute("score");

        // Adiciona atributos ao modelo
        model.addAttribute("lacuna", palavraAtual.getComLacunas());
        model.addAttribute("indices", palavraAtual.getIndicesFaltando());
        model.addAttribute("rodada", rodada);
        model.addAttribute("score", score);
        model.addAttribute("traducaoTupi", palavraAtual.getTraducaoTupi());

        // Remove mensagem da sessão se existir
        if (session.getAttribute("mensagem") != null) {
            model.addAttribute("mensagem", session.getAttribute("mensagem"));
            session.removeAttribute("mensagem");
        }

        return "palavreca.html";
    }

    @PostMapping("/verificar")
    public String verificarResposta(@RequestParam Map<String, String> params, HttpSession session) {
        // Verifica se há palavra atual na sessão
        if (session.getAttribute("palavraAtual") == null) {
            return "redirect:/palavreca";
        }

        Palavreca palavraSessao = (Palavreca) session.getAttribute("palavraAtual");
        session.removeAttribute("palavraAtual");

        // Processa as respostas
        Map<Integer, Character> respostas = new HashMap<>();
        for (String key : params.keySet()) {
            if (key.startsWith("letra")) {
                int idx = Integer.parseInt(key.replace("letra", ""));
                char c = params.get(key).charAt(0);
                respostas.put(idx, c);
            }
        }

        // Valida resposta
        boolean acertou = palavraService.validarResposta(palavraSessao, respostas);

        // Atualiza pontuação
        Integer rodada = (Integer) session.getAttribute("rodada");
        Integer score = (Integer) session.getAttribute("score");
        Integer acertos = (Integer) session.getAttribute("acertos");

        if (acertou) {
            score += 10;
            acertos++;
            session.setAttribute("mensagem", "✅ Parabéns! Você acertou!");
        } else {
            session.setAttribute("mensagem", "❌ Você errou!");
        }

        rodada++;
        session.setAttribute("rodada", rodada);
        session.setAttribute("score", score);
        session.setAttribute("acertos", acertos);

        // Verifica fim do jogo
        if (rodada > 10) {
            return "redirect:/palavreca/final";
        }

        // Gera nova palavra para próxima rodada
        Palavreca novaPalavra = palavraService.getPalavraAleatoria();
        session.setAttribute("palavraAtual", novaPalavra);

        return "redirect:/palavreca";
    }

    @GetMapping("/palavreca/final")
    public String exibirFinal(Model model, HttpSession session) {
        model.addAttribute("acertos", session.getAttribute("acertos"));
        model.addAttribute("score", session.getAttribute("score"));
        // Salva pontuação no banco (descomente quando necessário)
        /*
        PontuacaoDTO dto = new PontuacaoDTO(
                JOGO_ID,
                SALA_COD,
                ALUNO,
                (Integer) session.getAttribute("score"),
                (Integer) session.getAttribute("acertos")
        );
        pontuacaoService.setPontuacao(dto);
        */

        return "palavrecaFinal.html";
    }

    @GetMapping("/reiniciar")
    public String reiniciarJogo(HttpSession session) {
        palavraService.resetPalavrasUsadas();
        session.invalidate();
        return "redirect:/palavreca";
    }

    private void iniciarSessaoSeNecessario(HttpSession session) {
        if (session.getAttribute("rodada") == null || (Integer) session.getAttribute("rodada") > 10) {
            session.setAttribute("rodada", 1);
            session.setAttribute("score", 0);
            session.setAttribute("acertos", 0);
        }
    }
}