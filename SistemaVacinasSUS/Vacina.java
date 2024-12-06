package SistemaVacinasSUS;

public class Vacina {
	private int id;
	private String nome;
	private String fabricante;
	private Lote lote;
	private int dosesRecomendadas;
	private int intervaloEntreDoses;

	public Vacina(int id, String nome, String fabricante, int dosesRecomendadas, int intervaloEntreDoses, Lote lote) {
		this.id = id;
		this.nome = nome;
		this.fabricante = fabricante;
		this.dosesRecomendadas = dosesRecomendadas;
		this.intervaloEntreDoses = intervaloEntreDoses;
		this.lote = lote;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public int getDosesRecomendadas() {
		return dosesRecomendadas;
	}

	public void setDosesRecomendadas(int dosesRecomendadas) {
		this.dosesRecomendadas = dosesRecomendadas;
	}

	public int getIntervaloEntreDoses() {
		return intervaloEntreDoses;
	}

	public void setIntervaloEntreDoses(int intervaloEntreDoses) {
		this.intervaloEntreDoses = intervaloEntreDoses;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public String obterInformacoes() {
		return String.format("Vacina: %s, Fabricante: %s", nome, fabricante);
	}

	@Override
	public String toString() {
		return "Vacina [id=" + id + ", nome=" + nome + ", fabricante=" + fabricante + ", lote=" + lote
				+ ", dosesRecomendadas=" + dosesRecomendadas + ", intervaloEntreDoses=" + intervaloEntreDoses + "]";
	}
}
