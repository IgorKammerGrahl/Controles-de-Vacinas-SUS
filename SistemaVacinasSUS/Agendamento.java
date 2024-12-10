package SistemaVacinasSUS;

import java.util.Date;
import java.util.List;

public class Agendamento {
	public enum StatusAgendamento { PENDENTE, CONFIRMADO, CANCELADO }

	private int id;
	private Cidadao cidadao;
	private List<Vacina> vacinas;
	private Date dataAgendamento;
	private LocalVacinacao local;
	private StatusAgendamento status;

	public Agendamento(int id, Cidadao cidadao, List<Vacina> vacinas, Date dataAgendamento, LocalVacinacao local) {
		if (vacinas == null || vacinas.isEmpty()) {
			throw new IllegalArgumentException("Vacinas associadas não podem ser nulas ou vazias");
		}
		if (local == null) {
			throw new IllegalArgumentException("Local de vacinação é obrigatório.");
		}
		this.id = id;
		this.cidadao = cidadao;
		this.vacinas = vacinas;
		this.dataAgendamento = dataAgendamento;
		this.local = local;
		this.status = StatusAgendamento.PENDENTE;
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

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public LocalVacinacao getLocal() {
		return local;
	}

	public void setLocal(LocalVacinacao local) {
		this.local = local;
	}

	public StatusAgendamento getStatus() {
		return status;
	}

	public void confirmarAgendamento() {
		if (status == StatusAgendamento.PENDENTE) {
			this.status = StatusAgendamento.CONFIRMADO;
			System.out.println("✔️ Agendamento confirmado para o cidadão: " + cidadao.getNome());
		} else {
			System.out.println("⚠️ O agendamento já foi " + status.toString().toLowerCase() + ".");
		}
	}

	public void cancelarAgendamento() {
		if (status == StatusAgendamento.PENDENTE) {
			this.status = StatusAgendamento.CANCELADO;
			System.out.println("✔️ Agendamento cancelado para o cidadão: " + cidadao.getNome());
		} else {
			System.out.println("⚠️ O agendamento já foi " + status.toString().toLowerCase() + ".");
		}
	}

	public String visualizarAgendamento() {
		StringBuilder detalhes = new StringBuilder();
		detalhes.append("Agendamento [ID: ").append(id).append("]\n")
		.append("Cidadão: ").append(cidadao.getNome()).append("\n")
		.append("Local: ").append(local.getNome()).append("\n")
		.append("Data: ").append(dataAgendamento).append("\n")
		.append("Vacinas: ");
		for (Vacina vacina : vacinas) {
			detalhes.append(vacina.getNome()).append(", ");
		}
		detalhes.append("\nStatus: ").append(status);
		return detalhes.toString();
	}
}
