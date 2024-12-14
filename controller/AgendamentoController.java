package controller;

import dao.*;
import SistemaVacinasSUS.*;
import java.util.List;

class AgendamentoController {
	private final AgendamentoDAO agendamentoDAO;
	private final CidadaoDAO cidadaoDAO;
	private final LocalVacinacaoDAO localVacinacaoDAO;
	private final VacinaDAO vacinaDAO;

	public AgendamentoController() {
		this.agendamentoDAO = new AgendamentoDAO();
		this.cidadaoDAO = new CidadaoDAO();
		this.localVacinacaoDAO = new LocalVacinacaoDAO();
		this.vacinaDAO = new VacinaDAO();
	}

	public boolean criarAgendamento(int cidadaoId, int localId, List<Integer> vacinasIds, java.util.Date data) {
		Cidadao cidadao = cidadaoDAO.buscarPorId(cidadaoId);
		LocalVacinacao local = localVacinacaoDAO.buscarPorId(localId);
		List<Vacina> vacinas = vacinaDAO.buscarVacinasPorIds(vacinasIds);

		if (cidadao == null || local == null || vacinas.isEmpty()) {
			System.out.println("Dados inválidos para agendamento.");
			return false;
		}

		Agendamento agendamento = new Agendamento(0, cidadao, vacinas, data, local);
		return agendamentoDAO.inserir(agendamento);
	}

	public List<Agendamento> listarAgendamentos() {
		return agendamentoDAO.listarTodos();
	}

	public boolean confirmarAgendamento(int agendamentoId) {
		return agendamentoDAO.atualizarStatus(agendamentoId, Agendamento.StatusAgendamento.CONFIRMADO);
	}

	public boolean cancelarAgendamento(int agendamentoId) {
		return agendamentoDAO.atualizarStatus(agendamentoId, Agendamento.StatusAgendamento.CANCELADO);
	}
}
