package SistemaVacinasSUS;

import java.util.Date;

public class Lote {
	private int id;
	private String numeroLote;
	private Date validade;
	private int quantidade;

	public Lote(int id, String numeroLote, Date validade, int quantidade) {
		this.id = id;
		this.numeroLote = numeroLote;
		this.validade = validade;
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public boolean verificarEstoque() {
		return quantidade > 0;
	}

	@Override
	public String toString() {
		return "Lote [id=" + id + ", numeroLote=" + numeroLote + ", validade=" + validade + ", quantidade=" + quantidade
				+ "]";
	}
}
