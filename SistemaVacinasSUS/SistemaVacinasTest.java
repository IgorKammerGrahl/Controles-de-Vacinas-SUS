package SistemaVacinasSUS;

import java.util.*;

public class SistemaVacinasTest {
	public static void main(String[] args) {
		testarVacina();
		testarLote();
		testarUsuario();
		testarAgendamento();
	}

	private static void testarVacina() {
		System.out.println("\n[Teste: Vacina]");
		try {
			Lote lote = new Lote(1, "L12345", new Date(), 100);
			Vacina vacina = new Vacina(1, "COVID-19", "Pfizer", 2, 21, lote);
			System.out.println("Vacina criada com sucesso: " + vacina.obterInformacoes());
		} catch (Exception e) {
			System.out.println("Falha ao criar vacina: " + e.getMessage());
		}

		try {
			new Vacina(1, "COVID-19", "Pfizer", -1, 21, null);
			System.out.println("Erro esperado para doses negativas não foi lançado.");
		} catch (IllegalArgumentException e) {
			System.out.println("Exceção correta para doses negativas: " + e.getMessage());
		}
	}

	private static void testarLote() {
		System.out.println("\n[Teste: Lote]");
		try {
			Lote lote = new Lote(1, "L12345", new Date(), 100);
			System.out.println("Lote criado com sucesso.");
		} catch (Exception e) {
			System.out.println("Falha ao criar lote válido: " + e.getMessage());
		}

		try {
			new Lote(1, "L54321", new Date(), -10);
			System.out.println("Erro esperado para quantidade negativa não foi lançado.");
		} catch (IllegalArgumentException e) {
			System.out.println("Exceção correta para quantidade negativa: " + e.getMessage());
		}
	}

	private static void testarUsuario() {
		System.out.println("\n[Teste: Usuario]");
		Usuario usuario = new Cidadao(1, "Ana", "111.222.333-44", new Date(), "ana@gmail.com", "senha123");
		if (usuario.autenticar("ana@gmail.com", "senha123")) {
			System.out.println("Autenticação com dados corretos passou.");
		} else {
			System.out.println("Autenticação com dados corretos falhou.");
		}

		try {
			usuario.autenticar(null, "senha123");
			System.out.println("Erro esperado para email nulo não foi lançado.");
		} catch (IllegalArgumentException e) {
			System.out.println("Exceção correta para email nulo: " + e.getMessage());
		}
	}

	private static void testarAgendamento() {
		System.out.println("\n[Teste: Agendamento]");
		try {
			Cidadao cidadao = new Cidadao(1, "João", "123.456.789-00", new Date(), "joao@gmail.com", "senha123");
			LocalVacinacao local = new LocalVacinacao(1, "Posto de Saúde", "Rua 123", "123456789");
			Vacina vacina = new Vacina(1, "COVID-19", "Pfizer", 2, 21, null);
			Agendamento agendamento = new Agendamento(1, cidadao, List.of(vacina), new Date(), local);
			agendamento.confirmarAgendamento();
			System.out.println("Agendamento confirmado com dados válidos.");
		} catch (Exception e) {
			System.out.println("Falha ao confirmar agendamento válido: " + e.getMessage());
		}

		try {
			Cidadao cidadao = new Cidadao(1, "João", "123.456.789-00", new Date(), "joao@gmail.com", "senha123");
			LocalVacinacao local = new LocalVacinacao(1, "Posto de Saúde", "Rua 123", "123456789");
			new Agendamento(1, cidadao, null, new Date(), local);
			System.out.println("Erro esperado para agendamento sem vacinas não foi lançado.");
		} catch (IllegalArgumentException e) {
			System.out.println("Exceção correta para agendamento sem vacinas: " + e.getMessage());
		}
	}
}

