package controller;

import dao.*;
import SistemaVacinasSUS.*;
import java.util.List;

public class VacinacaoController {
	private final VacinacaoDAO vacinacaoDAO;
	private final CidadaoDAO cidadaoDAO;
	private final AgenteDeSaudeDAO agenteDeSaudeDAO;
	private final LocalVacinacaoDAO localVacinacaoDAO;

	public VacinacaoController() {
		this.vacinacaoDAO = new VacinacaoDAO();
		this.cidadaoDAO = new CidadaoDAO();
		this.agenteDeSaudeDAO = new AgenteDeSaudeDAO();
		this.localVacinacaoDAO = new LocalVacinacaoDAO();
	}

	public boolean registrarVacinacao(int cidadaoId, int agenteId, List<Integer> vacinasIds, java.util.Date data, int localId) {
	    Cidadao cidadao = cidadaoDAO.buscarPorId(cidadaoId);
	    AgenteDeSaude agente = agenteDeSaudeDAO.buscarPorId(agenteId);
	    LocalVacinacao local = localVacinacaoDAO.buscarPorId(localId);
	    List<Vacina> vacinas = vacinacaoDAO.buscarVacinasPorIds(vacinasIds);

	    System.out.println("Vacinas encontradas: " + vacinas);

	    if (cidadao == null || agente == null || local == null || vacinas.isEmpty()) {
	        System.out.println("Dados inválidos para vacinação.");
	        return false;
	    }

	    Vacinacao vacinacao = new Vacinacao(0, cidadao, agente, vacinas, data, local);
	    return vacinacaoDAO.salvar(vacinacao);
	}

	public List<Vacinacao> listarVacinacoes() {
		return vacinacaoDAO.listarTodos();
	}
}
