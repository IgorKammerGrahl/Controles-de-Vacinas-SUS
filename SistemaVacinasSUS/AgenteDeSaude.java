package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.VacinacaoDAO;

public class AgenteDeSaude extends Usuario {
	private List<Vacinacao> vacinacoesRealizadas;

	public AgenteDeSaude(int id, String nome, String cpf, Date dataNascimento, String email, String senha) {
		super(id, nome, cpf, dataNascimento, email, senha, PerfilUsuario.AGENTE_DE_SAUDE);
		this.vacinacoesRealizadas = new ArrayList<>();
	}

	public void registrarVacinacao(Vacinacao vacinacao) {
		vacinacoesRealizadas.add(vacinacao);
	}

	public String gerarRelatorioVacinas() {
		VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();
		List<Vacinacao> vacinacoesRealizadas = vacinacaoDAO.buscarVacinacoesPorAgenteId(this.getId());

		if (vacinacoesRealizadas.isEmpty()) {
			return "Nenhuma vacina aplicada por este agente.";
		}

		StringBuilder relatorio = new StringBuilder();
		Map<String, Integer> contagemVacinas = new HashMap<>();

		for (Vacinacao vacinacao : vacinacoesRealizadas) {
			for (Vacina vacina : vacinacao.getVacinas()) {
				contagemVacinas.put(
						vacina.getNome(),
						contagemVacinas.getOrDefault(vacina.getNome(), 0) + 1
						);
			}
		}

		relatorio.append("Relatório Geral de Vacinas Aplicadas:\n");
		contagemVacinas.forEach((vacina, quantidade) ->
		relatorio.append(String.format("- Vacina: %s | Total Aplicadas: %d\n", vacina, quantidade))
				);

		return relatorio.toString();
	}

	public String gerarRelatorioCidadaosAtendidos() {
		VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();
		List<Vacinacao> vacinacoesRealizadas = vacinacaoDAO.buscarVacinacoesPorAgenteId(this.getId());

		if (vacinacoesRealizadas.isEmpty()) {
			return "Nenhum cidadão atendido por este agente.";
		}

		StringBuilder relatorio = new StringBuilder();
		relatorio.append("Cidadãos Atendidos:\n");

		vacinacoesRealizadas.forEach(vacinacao -> {
			Cidadao cidadao = vacinacao.getCidadao();
			relatorio.append(String.format("Nome: %s, CPF: %s\n", cidadao.getNome(), cidadao.getCpf()));
		});

		return relatorio.toString();
	}
}

