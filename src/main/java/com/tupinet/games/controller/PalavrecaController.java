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

    private Palavreca palavraAtual;

    private final Integer JOGO_ID = 1; // defina o ID do jogo "Palavreca" quando ele é escolhido na criação da sala
    private final String SALA_COD = "PORT123"; // defina o ID da sala
    private final String ALUNO = "Luan"; // aqui poderá buscar da sala futuramente

    @GetMapping("/palavreca")
    public String exibirJogo(Model model, HttpSession session) {
        iniciarSessaoSeNecessario(session);

        palavraAtual = palavraService.getPalavraAleatoria();
        session.setAttribute("palavraAtual", palavraAtual);

        model.addAttribute("lacuna", palavraAtual.getComLacunas());
        model.addAttribute("indices", palavraAtual.getIndicesFaltando());
        model.addAttribute("rodada", session.getAttribute("rodada"));
        model.addAttribute("score", session.getAttribute("score"));
        model.addAttribute("traducaoTupi", palavraAtual.getTraducaoTupi());
        return "palavreca.html";
    }

    @PostMapping("/verificar")
    public String verificarResposta(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        Palavreca palavraSessao = (Palavreca) session.getAttribute("palavraAtual");

        Map<Integer, Character> respostas = new HashMap<>();
        for (String key : params.keySet()) {
            if (key.startsWith("letra")) {
                int idx = Integer.parseInt(key.replace("letra", ""));
                char c = params.get(key).charAt(0);
                respostas.put(idx, c);
            }
        }

        boolean acertou = palavraService.validarResposta(palavraSessao, respostas);

        // atualiza score e rodada
        int rodada = (int) session.getAttribute("rodada");
        int score = (int) session.getAttribute("score");
        int acertos = (int) session.getAttribute("acertos");

        if (acertou) {
            score += 10;
            acertos++;
            model.addAttribute("mensagem", "✅ Parabéns! Você acertou!");
        } else {
            model.addAttribute("mensagem", "❌ Você errou!");
        }

        rodada++;

        session.setAttribute("rodada", rodada);
        session.setAttribute("score", score);
        session.setAttribute("acertos", acertos);

        // verifica se acabou
        if (rodada > 10) {
            model.addAttribute("acertos", acertos);
            model.addAttribute("score", score);

            // salva a pontuação final no banco no final do jogo
            PontuacaoDTO dto = new PontuacaoDTO(
                    JOGO_ID,
                    SALA_COD,
                    ALUNO,
                    score,
                    acertos
            );
            pontuacaoService.setPontuacao(dto);

            return "palavrecaFinal.html";
        }

        // próxima palavra
        palavraAtual = palavraService.getPalavraAleatoria();
        session.setAttribute("palavraAtual", palavraAtual);

        model.addAttribute("lacuna", palavraAtual.getComLacunas());
        model.addAttribute("indices", palavraAtual.getIndicesFaltando());
        model.addAttribute("rodada", rodada);
        model.addAttribute("score", score);
        model.addAttribute("traducaoTupi", palavraAtual.getTraducaoTupi());

        return "palavreca.html";
    }

    @GetMapping("/reiniciar")
    public String reiniciarJogo(HttpSession session) {
        session.invalidate();
        return "redirect:/palavreca";
    }

    private void iniciarSessaoSeNecessario(HttpSession session) {
        if (session.getAttribute("rodada") == null) {
            session.setAttribute("rodada", 1);
            session.setAttribute("score", 0);
            session.setAttribute("acertos", 0);
        }
    }
}
