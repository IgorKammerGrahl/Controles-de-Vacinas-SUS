package dao;

import SistemaVacinasSUS.AgenteDeSaude;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TesteAgenteDeSaudeDAO {
	public static void main(String[] args) {
		LocalDate localDate = LocalDate.of(2005, 2, 14); 
		Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		AgenteDeSaude agente = new AgenteDeSaude(
				0, 
				"Igor Kammer Grahl", 
				"49823456987",  
				dataNascimento, 
				"igorkramigra@gmail.com", 
				"senhaSerelepi123"
				);

		AgenteDeSaudeDAO agenteDAO = new AgenteDeSaudeDAO();

		if (agenteDAO.verificarCpf(agente.getCpf())) {
			System.out.println("CPF já cadastrado! Inserção não permitida.");
		} else {
			boolean sucesso = agenteDAO.inserir(agente);

			if (sucesso) {
				System.out.println("Agente de saúde inserido com sucesso!");
			} else {
				System.out.println("Falha ao inserir o agente de saúde.");
			}
		}

		System.out.println("Lista de agentes de saúde:");
		for (AgenteDeSaude a : agenteDAO.listarTodos()) {
			System.out.println(a);
		}
	}
}