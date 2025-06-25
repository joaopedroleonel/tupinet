let tentativas = 0;
let acertos = 0;
let pontuacao = 0;
let current = { word: '', translate: '' };

window.addEventListener('load', () => {
  current.word = document.getElementById('palavra').innerText;
  current.translate = document.getElementById('traducao').innerText;
  preencherContadores();
  activeMicrophone();
});

function preencherContadores() {
  document.getElementById('tentativas').innerText = tentativas;
  document.getElementById('pontuacao').innerText = pontuacao;
}

async function carregarProxima() {
  const res = await fetch('/traducao-aleatoria');
  if (!res.ok) return;
  const json = await res.json();
  current.word = json.palavra;
  current.translate = json.traducao;
  document.getElementById('palavra').innerText = current.word;
  document.getElementById('traducao').innerText = current.translate;
  document.getElementById('takeToTalk').disabled = false;
}

function verifyAnswer(message) {
  const clean = message
    .toUpperCase()
    .replace(/[^A-ZÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕÄËÏÖÜÇ]/g, '');
  return clean.includes(current.word.toUpperCase());
}

async function transcribe(audio) {
  const resp = await fetch('/transcrever', {
    method: 'POST',
    headers: { 'Content-Type': 'audio/wav' },
    body: new File([audio], 'audio/wav')
  });
  return resp.ok ? resp.json() : null;
}

function exibirFeedback(certo) {
  const notif = document.getElementById('notification');
  notif.className = 'notification show ' + (certo ? 'certo' : 'errado');
  notif.innerText = certo ? '✔️ Acertou!' : '❌ Errou!';
  setTimeout(() => notif.classList.remove('show'), 2000);
}

function activeMicrophone() {
  let mediaRecorder, audioChunks = [], listening = false;
  const btn = document.getElementById('takeToTalk');
  btn.disabled = false;

  navigator.mediaDevices.getUserMedia({ audio: true })
    .then(stream => {
      mediaRecorder = new MediaRecorder(stream);

      mediaRecorder.ondataavailable = e => audioChunks.push(e.data);
      mediaRecorder.onstop = async () => {
        btn.disabled = true;
        document.getElementById('loader').style.display = 'flex';
        const blob = new Blob(audioChunks, { type: 'audio/wav' });
        const result = await transcribe(blob);
        document.getElementById('loader').style.display = 'none';

        const certo = result && verifyAnswer(result.text);

        if (tentativas < 10) {
          tentativas++;
          if (certo) {
            pontuacao += 10;
            acertos++;
            exibirFeedback(true);
            preencherContadores();
            setTimeout(() => carregarProxima(), 500);
          } else {
            exibirFeedback(false);
            preencherContadores();
            setTimeout(() => btn.disabled = false, 2000);
          }
        } else {
          const game = document.getElementById('game');
          game.innerHTML = '';

          const fim = document.createElement('h1');
          fim.innerText = 'Fim de jogo!';
          fim.classList.add('traducao-title');

          const recomecar = document.createElement('button');
          recomecar.innerText = 'Jogar novamente';
          recomecar.classList.add('botao-padrao');
          recomecar.style.backgroundColor = '#4CAF50';
          recomecar.addEventListener('click', () => {window.location.reload();});

          const sair = document.createElement('a');
          sair.innerText = 'Sair do Jogo';
          sair.classList.add('botao-padrao');
          const codigo = sessionStorage.getItem('codigoSala');
          if (codigo) {
            sair.href = '/selecaoJogos?codigo=' + encodeURIComponent(codigo);
          } else {
            sair.href = '/';
          }
          sair.style.backgroundColor = '#242424';

          const div = document.createElement('div');
          div.classList.add('traducao');
          div.style.gap = '10px';
          div.append(fim, recomecar, sair);
          game.appendChild(div);

          fetch('/salvar-pontuacao', {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                jogoId: sessionStorage.getItem('jogoId'),
                salaCod: sessionStorage.getItem('codigoSala'),
                aluno: sessionStorage.getItem('nomeAluno'),
                pontos: pontuacao,
                acertos: acertos
            })
          });

        }

      };

      btn.onclick = () => {
        if (!listening) {
          audioChunks = [];
          mediaRecorder.start();
          btn.innerHTML = '<i class="fa-solid fa-square"></i>';
        } else {
          mediaRecorder.stop();
          btn.innerHTML = '<i class="fa-solid fa-microphone"></i>';
        }
        listening = !listening;
      };
    })
    .catch(console.error);
}