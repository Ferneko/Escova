import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	private static Jogo jogo;

	public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(6789);

		// Conecta o Jogador 1
		Socket sj1 = socket.accept();
		ObjectInputStream doJogador1 = new ObjectInputStream(sj1.getInputStream());
		ObjectOutputStream paraJogador1 = new ObjectOutputStream(sj1.getOutputStream());

		Jogador j1 = (Jogador) doJogador1.readObject();
		System.out.println(j1.getNome() + " entrou no jogo. Aguardando segundo jogador.");

		// Conecta o Jogador 2
		Socket sj2 = socket.accept();
		ObjectInputStream doJogador2 = new ObjectInputStream(sj2.getInputStream());
		ObjectOutputStream paraJogador2 = new ObjectOutputStream(sj2.getOutputStream());

		Jogador j2 = (Jogador) doJogador2.readObject();
		System.out.println(j2.getNome() + " entrou no jogo. Iniciando partida.");

		// Inicia o jogo com os dois jogadores
		jogo = new Jogo(j1, j2);
		j1.setAdversario(j2);
		j2.setAdversario(j1);

		int partida = 0;

		while (!jogo.jogoAcabou()) {
			partida++;
			System.out.println("Início de nova partida.");
			System.out.println("Pontos " + j1.getNome() + ": " + j1.getPontos());
			System.out.println("Pontos " + j2.getNome() + ": " + j2.getPontos());
			jogo.novaMao();
			while (!jogo.partidaAcabou()) {
				jogo.novaRodada();
				while (!jogo.rodadaAcabou()) {
					// intercala jogador inicial a cada partida
					if (partida % 2 != 0) {
						solicitarJogada(paraJogador1, j1);
						receberJogada(doJogador1, j1);
						solicitarJogada(paraJogador2, j2);
						receberJogada(doJogador2, j2);
					} else {
						solicitarJogada(paraJogador2, j2);
						receberJogada(doJogador2, j2);
						solicitarJogada(paraJogador1, j1);
						receberJogada(doJogador1, j1);
					}
				}
			}
		}

		// Jogo terminou
		paraJogador1.reset();
		paraJogador1.writeObject("FIM");
		paraJogador2.reset();
		paraJogador2.writeObject("FIM");

		jogo.exibeResultados();

		sj1.close();
		sj2.close();
		socket.close();
	}

	public static void solicitarJogada(ObjectOutputStream paraJogador, Jogador jogador) {
		try {
			// Jogo não terminou
			paraJogador.reset();
			paraJogador.writeObject("OK");
			// Envia as cartas da mesa para o jogador
			paraJogador.reset();
			paraJogador.writeObject(jogo.getMesa());
			// Envia as cartas da mão para jogador e aguarda sua jogada
			paraJogador.reset();
			paraJogador.writeObject(jogador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void receberJogada(ObjectInputStream doJogador, Jogador jogador) {
		try {
			// Recebe sua jogada
			Jogada jogada = (Jogada) doJogador.readObject();

			jogo.setMesa(jogada.getMesa()); // Atualiza a mesa
			jogador.setMao(jogada.getMao()); // Atualiza sua mão
			// Se capturou alguma carta
			if (jogada.getCapturadas().size() > 0) {
				// Insere-as na pilha
				jogador.insereNaPilha(jogada.getCapturadas());
				// Verifica se fez ESCOVA
				if (jogo.getMesa().size() == 0)
					jogador.setEscovas(jogador.getEscovas() + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
