package controller;

import controller.*;
import SistemaVacinasSUS.*;
import java.util.*;

public class SistemaVacinasControllerTeste {

	public static void main(String[] args) {
		CidadaoController cidadaoController = new CidadaoController();
		AgendamentoController agendamentoController = new AgendamentoController();
		VacinacaoController vacinacaoController = new VacinacaoController();

		System.out.println("=== Testando CidadaoController ===");
		List<Cidadao> cidadaos = cidadaoController.listarCidadaos();
		System.out.println("Cidadãos cadastrados:");
		cidadaos.forEach(System.out::println);

		System.out.println("\n=== Testando AgendamentoController ===");
		int cidadaoId = 1; 
		int localId = 2; 
		List<Integer> vacinasIds = Arrays.asList(4); 
		Date dataAgendamento = new Date(); 

		System.out.println("Criando agendamento...");
		boolean agendamentoCriado = agendamentoController.criarAgendamento(cidadaoId, localId, vacinasIds, dataAgendamento);
		if (agendamentoCriado) {
			System.out.println("Agendamento criado com sucesso!");
		} else {
			System.out.println("Falha ao criar o agendamento.");
		}

		System.out.println("Listando agendamentos:");
		List<Agendamento> agendamentos = agendamentoController.listarAgendamentos();
		agendamentos.forEach(agendamento -> System.out.println(agendamento.visualizarAgendamento()));

		if (!agendamentos.isEmpty()) {
			int agendamentoId = agendamentos.get(0).getId();
			System.out.println("\nConfirmando o primeiro agendamento...");
			boolean confirmado = agendamentoController.confirmarAgendamento(agendamentoId);
			System.out.println(confirmado ? "Agendamento confirmado com sucesso!" : "Falha ao confirmar o agendamento.");

			System.out.println("Cancelando o primeiro agendamento...");
			boolean cancelado = agendamentoController.cancelarAgendamento(agendamentoId);
			System.out.println(cancelado ? "Agendamento cancelado com sucesso!" : "Falha ao cancelar o agendamento.");
		}

		System.out.println("\n=== Testando VacinacaoController ===");
		int agenteId = 1; 
		System.out.println("Registrando vacinação...");
		boolean vacinacaoRegistrada = vacinacaoController.registrarVacinacao(cidadaoId, agenteId, vacinasIds, new Date(), localId);
		System.out.println(vacinacaoRegistrada ? "Vacinação registrada com sucesso!" : "Falha ao registrar vacinação.");

		System.out.println("Listando vacinações:");
		List<Vacinacao> vacinacoes = vacinacaoController.listarVacinacoes();
		vacinacoes.forEach(vacinacao -> System.out.println(vacinacao.emitirComprovante()));
	}
}
