package SistemaVacinasSUS;

import java.util.Date;

public class Vacinacao {
	private int id;
	private Cidadao cidadao;
	private AgenteDeSaude agente;
	private Vacina vacina;
	private Date data;

	public Vacinacao(int id, Cidadao cidadao, AgenteDeSaude agente, Vacina vacina, Date data) {
		this.id = id;
		this.cidadao = cidadao;
		this.agente = agente;
		this.vacina = vacina;
		this.data = data;
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

	public String emitirComprovante() {
		return String.format("Comprovante de vacinação:\nCidadão: %s\nVacina: %s\nData: %s", 
				cidadao.getNome(), vacina.obterInformacoes(), data.toString());
	}


}
