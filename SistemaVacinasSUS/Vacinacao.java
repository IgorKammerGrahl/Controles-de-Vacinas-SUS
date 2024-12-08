package SistemaVacinasSUS;

import java.util.Date;

public class Vacinacao {
	private int id;
	private Cidadao cidadao;
	private AgenteDeSaude agente;
	private Vacina vacina;
	private Lote lote;
	private Date data;
	private LocalVacinacao local;

	public Vacinacao(int id, Cidadao cidadao, AgenteDeSaude agente, Vacina vacina, Lote lote, Date data, LocalVacinacao local) {
		if (lote == null) {
			throw new IllegalArgumentException("Lote não pode ser nulo.");
		}
		if (lote.getQuantidade() <= 0) {
			throw new IllegalStateException("Estoque insuficiente para vacinação.");
		}
		if (local == null) {
			throw new IllegalArgumentException("Local de vacinação é obrigatório.");
		}
		this.id = id;
		this.cidadao = cidadao;
		this.agente = agente;
		this.vacina = vacina;
		this.lote = lote;
		this.data = data;
		this.local = local;

		lote.setQuantidade(lote.getQuantidade() - 1);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}
	public AgenteDeSaude getAgente() {
		return agente;
	}
	public void setAgente(AgenteDeSaude agente) {
		this.agente = agente;
	}
	public Vacina getVacina() {
		return vacina;
	}
	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public LocalVacinacao getLocal() {
		return local;
	}

	public void setLocal(LocalVacinacao local) {
		this.local = local;
	}

	public String emitirComprovante() {
		return String.format("Comprovante de vacinação:\nCidadão: %s\nVacina: %s\nData: %s", 
				cidadao.getNome(), vacina.obterInformacoes(), data.toString());
	}


}
