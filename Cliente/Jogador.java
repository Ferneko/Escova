import java.io.Serializable;
import java.util.ArrayList;

public class Jogador implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome; // nome do jogador
	private ArrayList<Carta> mao; // cartas na mão
	private ArrayList<Carta> pilha; // pilha de cartas adquiridas
	private int pontos; // total de pontos
	private int escovas; // quantidade de escovas
	private int ouros; // quantidade de ouros
	private boolean seteBelo; // tem sete de ouros?
	private boolean venceu;
	private Jogador adversario;

	public Jogador(String nome) {
		this.nome = nome;
		this.mao = new ArrayList<Carta>();
		this.pilha = new ArrayList<Carta>();
		this.pontos = 0;
		this.escovas = 0;
		this.ouros = 0;
		this.seteBelo = false;
		this.venceu = false;
		this.adversario = null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Jogador getAdversario() {
		return adversario;
	}

	public void setAdversario(Jogador jogador) {
		this.adversario = jogador;
	}

	public ArrayList<Carta> getMao() {
		return mao;
	}

	public void setMao(ArrayList<Carta> mao) {
		this.mao = mao;
	}

	public ArrayList<Carta> getPilha() {
		return pilha;
	}
	
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int getPontos() {
		setPontos(getPontos() + calculaPontosRodada());
		return pontos;
	}

	public void insereNaPilha(ArrayList<Carta> cartas) {
		for (Carta carta : cartas) {
			pilha.add(carta);
			if (carta.getNaipe() == "Ouros") {
				this.setOuros(ouros + 1);
				if (carta.getValor() == "7")
					this.seteBelo = true;
			}
		}
	}

	public int getEscovas() {
		return escovas;
	}

	public void setEscovas(int escovas) {
		this.escovas = escovas;
	}

	public int getOuros() {
		return ouros;
	}

	public void setOuros(int ouros) {
		this.ouros = ouros;
	}

	public boolean temSeteBelo() {
		return this.seteBelo;
	}
	
	public boolean venceu() {
		return this.venceu;
	}

	public boolean temMaioriaCartas() {
		if (adversario != null) {
			if (this.getPilha().size() > adversario.getPilha().size())
				return true;
		}
		return false;
	}

	public boolean temMaioriaOuros() {
		if (adversario != null) {
			if (this.getOuros() > adversario.getOuros())
				return true;
		}
		return false;
	}

	public int calculaPontosRodada() {
		int pontos = 0;
		// para cada escova: 1 ponto
		pontos += getEscovas();
		// por ter a maioria das cartas: 1 ponto
		if (temMaioriaCartas())
			pontos += 1;
		// por ter todas as cartas de Ouros (10): 2 pontos
		if (getOuros() == 10)
			pontos += 2;
		// por ter a maioria das cartas de Ouros: 1 ponto
		if (temMaioriaOuros())
			pontos += 1;
		// por ter o Sete de Ouros (Sete Belo): 1 ponto
		if (temSeteBelo())
			pontos += 1;
			
		ouros = 0;
		escovas = 0;
		seteBelo = false;
		
		return pontos;	
		
	}
}