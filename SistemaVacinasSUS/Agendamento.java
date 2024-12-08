package SistemaVacinasSUS;

import java.util.Date;
import java.util.List;

public class Agendamento {
	private int id;
	private Cidadao cidadao;
	private List<Vacina> vacinas;
	private Date dataAgendamento;
	private LocalVacinacao local;

	public Agendamento(int id, Cidadao cidadao, List<Vacina> vacinas, Date dataAgendamento, LocalVacinacao local) {
		if (vacinas == null || vacinas.isEmpty()) {
			throw new IllegalArgumentException("Vacinas associadas não podem ser nulas ou vazias");
		}
		this.id = id;
		this.cidadao = cidadao;
		this.vacinas = vacinas;
		this.dataAgendamento = dataAgendamento;
		this.local = local;
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

	public void confirmarAgendamento() {
		// Confirmar agendamento (simulado)
	}

}
