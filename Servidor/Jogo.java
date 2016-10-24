import java.util.ArrayList;
import java.util.Collections;

public class Jogo {
	private final int pontosVitoria = 6; // pontos para vencer o jogo
	private Jogador jogador1; // Jogador 1
	private Jogador jogador2; // Jogador 2
	private Baralho baralho; // Baralho de cartas
	private ArrayList<Carta> mesa; // Cartas atualmente na mesa (vísiveis)

	public Jogo(Jogador j1, Jogador j2) {
		this.jogador1 = j1;
		this.jogador2 = j2;
		this.mesa = new ArrayList<Carta>();
	}

	public void novaMao() {
		this.mesa = new ArrayList<Carta>();
		this.baralho = new Baralho();
		this.embaralhaCartas();
		this.distribuiCartasMesa();
	}

	public void novaRodada() {
		this.distribuiCartasJogadores();
	}

	private void embaralhaCartas() {
		Collections.shuffle(baralho.getCartas());
	}

	public void distribuiCartasJogadores() {
		// Distribui 3 cartas para cada jogador, de uma em uma
		int distribuicoes = 3;
		// Se não possuir 6 cartas, distribui o que tiver
		// igualmente para os 2 jogadores
		if (baralho.getCartas().size() < 6)
			distribuicoes = baralho.getCartas().size() / 2;

		for (int i = 0; i < distribuicoes; i++) {
			jogador1.getMao().add(baralho.getCartas().remove(baralho.getCartas().size() - 1));
			jogador2.getMao().add(baralho.getCartas().remove(baralho.getCartas().size() - 1));
		}
	}

	public void distribuiCartasMesa() {
		// Pega as próximas 4 cartas do baralho e deixa na mesa
		int distribuicoes = 4;
		// Se não possuir 4 cartas, distribui o que tiver
		if (baralho.getCartas().size() < 4)
			distribuicoes = baralho.getCartas().size();

		for (int i = 0; i < distribuicoes; i++) {
			this.getMesa().add(baralho.getCartas().remove(baralho.getCartas().size() - 1));
		}
	}

	public boolean jogoAcabou() {
		if (jogador1.getPontos() >= getPontosVitoria() || jogador2.getPontos() >= getPontosVitoria())
			return true;
		return false;
	}

	public boolean partidaAcabou() {
		if (this.getBaralho().getCartas().size() == 0)
			return true;
		return false;
	}

	public boolean rodadaAcabou() {
		if (jogador1.getMao().size() == 0 && jogador2.getMao().size() == 0)
			return true;
		return false;
	}

	public int getPontosVitoria() {
		return this.pontosVitoria;
	}

	public Jogador getJogador1() {
		return jogador1;
	}

	public void setJogador1(Jogador j1) {
		this.jogador1 = j1;
	}

	public Jogador getVencedor() {
		Jogador vencedor = jogador1;
		if (jogador2.getPontos() > jogador1.getPontos())
			vencedor = jogador2;
		return vencedor;
	}

	public Baralho getBaralho() {
		return baralho;
	}

	public ArrayList<Carta> getMesa() {
		return mesa;
	}

	public void setMesa(ArrayList<Carta> mesa) {
		this.mesa = mesa;
	}

	public void exibeResultados() {
		System.out.println("FIM DE JOGO!");
		System.out.println(this.getVencedor().getNome() + " VENCEU!");
		System.out.println("\nPlacar final:");
		System.out.println(this.getVencedor().getNome() + ": " + this.getVencedor().getPontos() + " pontos.");
		System.out.println(this.getVencedor().getAdversario().getNome() + ": "
				+ this.getVencedor().getAdversario().getPontos() + " pontos.");
	}
}
