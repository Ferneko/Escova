import java.io.Serializable;
import java.util.ArrayList;

public class Jogada implements Serializable {
	private static final long serialVersionUID = 1L;
	private Carta cartaMao; // Carta da mão do jogador
	private ArrayList<Carta> cartasMesa; // Cartas da mesa a serem adquiridas
	private ArrayList<Carta> mao; // Cartas na mão do jogador atualmente
	private ArrayList<Carta> mesa; // Cartas na mesa do jogo atualmente
	private ArrayList<Carta> capturadas; // Cartas capturadas pelo jogador

	public Jogada(Carta cartaMao, ArrayList<Carta> cartasMesa, ArrayList<Carta> mao, ArrayList<Carta> mesa) {
		this.cartaMao = cartaMao;
		this.cartasMesa = cartasMesa;
		this.mao = mao;
		this.mesa = mesa;
		this.capturadas = new ArrayList<Carta>();
	}

	public boolean efetuarJogada() {
		if (cartasMesa.size() == 0) { // Apenas descartar na mesa
			for (int i = 0; i < mao.size(); i++) {
				if (mao.get(i).getId() == cartaMao.getId()) {
					mao.remove(i);
					break;
				}
			}			
			mesa.add(cartaMao);
			return true;
		}

		// Jogada de 15 pontos
		int soma = cartaMao.getPontos();
		for (Carta carta : cartasMesa) {
			soma += carta.getPontos();
		}

		if (soma == 15) { // Jogada válida, somou 15 pontos

			// Remove da mão a carta jogada pelo jogador
			for (int i = 0; i < mao.size(); i++) {
				if (mao.get(i).getId() == cartaMao.getId()) {
					mao.remove(i);
					break;
				}
			}

			// Remove da mesa as cartas capturadas pelo jogador
			for (int i = 0; i < cartasMesa.size(); i++) {
				for (int j = 0; j < mesa.size(); j++) {
					if (mesa.get(j).getId() == cartasMesa.get(i).getId()) {
						mesa.remove(j);
					}
				}
			}

			// Insere nas cartas capturadas as cartas que somaram 15 pontos
			capturadas.add(cartaMao);
			capturadas.addAll(cartasMesa);
			return true;
		}
		return false; // Não efetuou 15 pontos na jogada
	}

	public Carta getCartaMao() {
		return cartaMao;
	}

	public ArrayList<Carta> getCartasMesa() {
		return cartasMesa;
	}

	public ArrayList<Carta> getMao() {
		return mao;
	}

	public ArrayList<Carta> getMesa() {
		return mesa;
	}

	public ArrayList<Carta> getCapturadas() {
		return capturadas;
	}

}