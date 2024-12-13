package SistemaVacinasSUS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Vacinacao {
	private int id;
	private Cidadao cidadao;
	private AgenteDeSaude agente;
	private List<Vacina> vacinas; 
	private Date data;
	private LocalVacinacao local;

	public Vacinacao(int id, Cidadao cidadao, AgenteDeSaude agente, List<Vacina> vacinas, Date data, LocalVacinacao local) {
		if (vacinas == null || vacinas.isEmpty()) {
			throw new IllegalArgumentException("A lista de vacinas não pode ser nula ou vazia.");
		}
		for (Vacina vacina : vacinas) {
			if (vacina.getLote() == null || vacina.getLote().getQuantidade() <= 0) {
				throw new IllegalStateException("Estoque insuficiente para a vacina: " + vacina.getNome());
			}
		}
		if (local == null) {
			throw new IllegalArgumentException("Local de vacinação é obrigatório.");
		}

		this.id = id;
		this.cidadao = cidadao;
		this.agente = agente;
		this.vacinas = vacinas;
		this.data = data;
		this.local = local;

		for (Vacina vacina : vacinas) {
			vacina.getLote().setQuantidade(vacina.getLote().getQuantidade() - 1);
		}
	}

	public Vacinacao(int id, Agendamento agendamento, AgenteDeSaude agente) {
		if (agendamento.getStatus() != Agendamento.StatusAgendamento.CONFIRMADO) {
			throw new IllegalStateException("A vacinação só pode ser realizada em agendamentos confirmados.");
		}
		List<Vacina> vacinas = agendamento.getVacinas();
		if (vacinas == null || vacinas.isEmpty()) {
			throw new IllegalArgumentException("Agendamento deve conter pelo menos uma vacina.");
		}
		for (Vacina vacina : vacinas) {
			if (vacina.getLote() == null || vacina.getLote().getQuantidade() <= 0) {
				throw new IllegalStateException("Estoque insuficiente para a vacina: " + vacina.getNome());
			}
		}

		this.id = id;
		this.cidadao = agendamento.getCidadao();
		this.agente = agente;
		this.vacinas = vacinas;
		this.data = agendamento.getDataAgendamento();
		this.local = agendamento.getLocal();

		for (Vacina vacina : vacinas) {
			vacina.getLote().setQuantidade(vacina.getLote().getQuantidade() - 1);
		}
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

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public LocalVacinacao getLocal() {
		return local;
	}

	public void setLocal(LocalVacinacao local) {
		this.local = local;
	}

	public String emitirComprovante() {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    StringBuilder comprovante = new StringBuilder();
	    comprovante.append("Comprovante de vacinação:\n");
	    comprovante.append("Cidadão: ").append(cidadao.getNome()).append("\n");
	    comprovante.append("Vacinas:\n");
	    for (Vacina vacina : vacinas) {
	        comprovante.append("- ").append(vacina.obterInformacoes()).append("\n");
	    }
	    comprovante.append("Data: ").append(sdf.format(data));
	    return comprovante.toString();
	}
}
