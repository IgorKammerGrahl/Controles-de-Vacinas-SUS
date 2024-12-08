package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	    StringBuilder relatorio = new StringBuilder();
	    Map<String, Integer> contagemVacinas = new HashMap<>();

	    for (Vacinacao vacinacao : vacinacoesRealizadas) {
	        String vacinaNome = vacinacao.getVacina().getNome();
	        contagemVacinas.put(vacinaNome, contagemVacinas.getOrDefault(vacinaNome, 0) + 1);
	    }

	    relatorio.append("Relatório Geral de Vacinas Aplicadas:\n");
	    for (Map.Entry<String, Integer> entry : contagemVacinas.entrySet()) {
	        relatorio.append(String.format("- Vacina: %s | Total Aplicadas: %d\n", entry.getKey(), entry.getValue()));
	    }

	    return relatorio.toString();
	}

	public String gerarRelatorioCidadaosAtendidos() {
		StringBuilder relatorio = new StringBuilder();
		relatorio.append("Cidadãos Atendidos:\n");
		for (Vacinacao vacinacao : vacinacoesRealizadas) {
			Cidadao cidadao = vacinacao.getCidadao();
			relatorio.append(String.format("Nome: %s, CPF: %s\n", cidadao.getNome(), cidadao.getCpf()));
		}
		return relatorio.toString();
	}
}

