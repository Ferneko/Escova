import java.io.Serializable;

public class Carta implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id; // Identificador numérico da carta
	private String valor; // Valor da carta (As, 2, 3...)
	private String naipe; // Naipe da carta (Ouros, Paus, Copas ou Espadas)
	private int pontos; // Pontos que valem cada carta

	public Carta(int id, String valor, String naipe, int pontos) {
		this.id = id;
		this.valor = valor;
		this.naipe = naipe;
		this.pontos = pontos;
	}

	public int getId() {
		return id;
	}

	public String getValor() {
		return valor;
	}

	public String getNaipe() {
		return naipe;
	}

	public int getPontos() {
		return pontos;
	}

	public String toString() {
		return "(" + getId() + ") " + getValor() + " de " + getNaipe();
	}
}
