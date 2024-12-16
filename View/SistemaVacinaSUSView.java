package View;

import controller.*;
import dao.*;
import SistemaVacinasSUS.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SistemaVacinaSUSView {
	private static final Scanner scanner = new Scanner(System.in);
	private final CidadaoController cidadaoController = new CidadaoController();
	private final AgendamentoController agendamentoController = new AgendamentoController();
	private final VacinacaoController vacinacaoController = new VacinacaoController();
	private final LocalVacinacaoDAO localVacinacaoDAO = new LocalVacinacaoDAO();
	private final AgenteDeSaudeDAO agenteDeSaudeDAO = new AgenteDeSaudeDAO();

	// ANSI color codes
	private static final String GREEN = "\u001B[32m";
	private static final String RED = "\u001B[31m";
	private static final String RESET = "\u001B[0m";

	public static void main(String[] args) {
		SistemaVacinaSUSView view = new SistemaVacinaSUSView();
		view.menuPrincipal();
	}

	public void menuPrincipal() {
		int opcao;
		do {
			System.out.println("\n=== Sistema Vacina SUS ===");
			System.out.println("1. Gerenciar Cidadãos");
			System.out.println("2. Gerenciar Agendamentos");
			System.out.println("3. Registrar Vacinação");
			System.out.println("4. Gerenciar Locais de Vacinação");
			System.out.println("5. Gerenciar Agentes de Saúde");
			System.out.println("6. Gerar Relatórios");
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1 -> menuCidadaos();
			case 2 -> menuAgendamentos();
			case 3 -> registrarVacinacao();
			case 4 -> menuLocais();
			case 5 -> menuAgentes();
			case 6 -> menuRelatorios();
			case 0 -> System.out.println("Encerrando sistema...");
			default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	// ============================ Menu Cidadãos ============================
	public void menuCidadaos() {
		int opcao;
		do {
			System.out.println("\n=== Gerenciar Cidadãos ===");
			System.out.println("1. Listar Cidadãos");
			System.out.println("2. Cadastrar Cidadão");
			System.out.println("3. Verificar Histórico de Vacinas");
			System.out.println("4. Listar Vacinas Pendentes");
			System.out.println("0. Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1 -> listarCidadaos();
			case 2 -> cadastrarCidadao();
			case 3 -> verificarHistoricoCidadao();
			case 4 -> listarVacinasPendentes();
			case 0 -> System.out.println("Voltando...");
			default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	public void listarCidadaos() {
		System.out.println("\n=== Lista de Cidadãos ===");
		List<Cidadao> cidadaos = cidadaoController.listarCidadaos();
		cidadaos.forEach(System.out::println);
	}

	public void cadastrarCidadao() {
		System.out.println("\n=== Cadastrar Cidadão ===");
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("CPF: ");
			String cpf = scanner.nextLine();
			System.out.print("Data de Nascimento (dd/MM/yyyy): ");
			Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Senha: ");
			String senha = scanner.nextLine();
			System.out.print("Endereço: ");
			String endereco = scanner.nextLine();

			Cidadao cidadao = new Cidadao(0, nome, cpf, dataNascimento, email, senha, endereco);
			if (cidadaoController.cadastrarCidadao(cidadao)) {
				System.out.println(GREEN + "✔️ Cidadão cadastrado com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ CPF já cadastrado." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao cadastrar cidadão: " + e.getMessage() + RESET);
		}
	}

	public void verificarHistoricoCidadao() {
		System.out.println("\n=== Verificar Histórico de Vacinas ===");
		System.out.print("ID do Cidadão: ");
		int cidadaoId = scanner.nextInt();
		scanner.nextLine();

		List<Vacina> historico = new VacinacaoDAO().buscarHistoricoVacinasPorCidadaoId(cidadaoId);
		if (historico.isEmpty()) {
			System.out.println(RED + "📋 Nenhuma vacina registrada para este cidadão." + RESET);
		} else {
			System.out.println(GREEN + "📋 Histórico de Vacinas:" + RESET);
			historico.forEach(vacina -> System.out.println("- " + vacina.obterInformacoes()));
		}
	}

	public void listarVacinasPendentes() {
		System.out.println("\n=== Listar Vacinas Pendentes ===");
		System.out.print("ID do Cidadão: ");
		int cidadaoId = scanner.nextInt();
		scanner.nextLine();

		List<Vacina> todasVacinas = new VacinaDAO().listarTodos();
		List<Vacina> historico = new VacinacaoDAO().buscarHistoricoVacinasPorCidadaoId(cidadaoId);

		List<Vacina> vacinasPendentes = todasVacinas.stream()
				.filter(vacina -> historico.stream().noneMatch(h -> h.getId() == vacina.getId()))
				.toList();

		if (vacinasPendentes.isEmpty()) {
			System.out.println(GREEN + "✅ Nenhuma vacina pendente." + RESET);
		} else {
			System.out.println(RED + "Vacinas Pendentes:" + RESET);
			vacinasPendentes.forEach(System.out::println);
		}
	}

	// ============================ Menu Agendamentos ============================
	public void menuAgendamentos() {
		int opcao;
		do {
			System.out.println("\n=== Gerenciar Agendamentos ===");
			System.out.println("1. Listar Agendamentos");
			System.out.println("2. Criar Agendamento");
			System.out.println("3. Confirmar Agendamento");
			System.out.println("4. Cancelar Agendamento");
			System.out.println("0. Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1 -> listarAgendamentos();
			case 2 -> criarAgendamento();
			case 3 -> atualizarStatusAgendamento(Agendamento.StatusAgendamento.CONFIRMADO);
			case 4 -> atualizarStatusAgendamento(Agendamento.StatusAgendamento.CANCELADO);
			case 0 -> System.out.println("Voltando...");
			default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	public void listarAgendamentos() {
		System.out.println("\n=== Lista de Agendamentos ===");
		List<Agendamento> agendamentos = agendamentoController.listarAgendamentos();
		agendamentos.forEach(agendamento -> System.out.println(agendamento.visualizarAgendamento()));
	}

	public void criarAgendamento() {
		System.out.println("\n=== Criar Agendamento ===");
		try {
			System.out.print("ID do Cidadão: ");
			int cidadaoId = scanner.nextInt();
			System.out.print("ID do Local de Vacinação: ");
			int localId = scanner.nextInt();
			System.out.print("Data de Agendamento (dd/MM/yyyy): ");
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next());
			System.out.print("IDs das Vacinas (separados por vírgula): ");
			List<Integer> vacinasIds = Arrays.stream(scanner.next().split(","))
					.map(Integer::parseInt)
					.toList();

			if (agendamentoController.criarAgendamento(cidadaoId, localId, vacinasIds, data)) {
				System.out.println(GREEN + "✔️ Agendamento criado com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ Falha ao criar agendamento." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao criar agendamento: " + e.getMessage() + RESET);
		}
	}

	public void atualizarStatusAgendamento(Agendamento.StatusAgendamento status) {
		System.out.print("ID do Agendamento: ");
		int agendamentoId = scanner.nextInt();
		if (agendamentoController.atualizarStatus(agendamentoId, status)) {
			System.out.println(GREEN + "✔️ Status atualizado com sucesso!" + RESET);
		} else {
			System.out.println(RED + "❌ Falha ao atualizar status." + RESET);
		}
	}

	// ============================ Menu Registrar Vacinação ============================
	public void registrarVacinacao() {
		System.out.println("\n=== Registrar Vacinação ===");
		try {
			System.out.print("ID do Cidadão: ");
			int cidadaoId = scanner.nextInt();
			System.out.print("ID do Agente de Saúde: ");
			int agenteId = scanner.nextInt();
			System.out.print("ID do Local de Vacinação: ");
			int localId = scanner.nextInt();
			System.out.print("Data da Vacinação (dd/MM/yyyy): ");
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next());
			System.out.print("IDs das Vacinas (separados por vírgula): ");
			List<Integer> vacinasIds = Arrays.stream(scanner.next().split(","))
					.map(Integer::parseInt)
					.toList();

			if (vacinacaoController.registrarVacinacao(cidadaoId, agenteId, vacinasIds, data, localId)) {
				System.out.println(GREEN + "✔️ Vacinação registrada com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ Falha ao registrar vacinação." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao registrar vacinação: " + e.getMessage() + RESET);
		}
	}

	// ============================ Menu Locais de Vacinação ============================
	public void menuLocais() {
		int opcao;
		do {
			System.out.println("\n=== Gerenciar Locais de Vacinação ===");
			System.out.println("1. Listar Locais");
			System.out.println("2. Cadastrar Local");
			System.out.println("3. Atualizar Local");
			System.out.println("4. Excluir Local");
			System.out.println("0. Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1 -> listarLocais();
			case 2 -> cadastrarLocal();
			case 3 -> atualizarLocal();
			case 4 -> excluirLocal();
			case 0 -> System.out.println("Voltando...");
			default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	public void listarLocais() {
		System.out.println("\n=== Lista de Locais ===");
		List<LocalVacinacao> locais = localVacinacaoDAO.listarTodos();
		locais.forEach(System.out::println);
	}

	public void cadastrarLocal() {
		System.out.println("\n=== Cadastrar Local de Vacinação ===");
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("Endereço: ");
			String endereco = scanner.nextLine();
			System.out.print("Telefone: ");
			String telefone = scanner.nextLine();

			LocalVacinacao local = new LocalVacinacao(0, nome, endereco, telefone);
			if (localVacinacaoDAO.inserir(local)) {
				System.out.println(GREEN + "✔️ Local cadastrado com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ Falha ao cadastrar local." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao cadastrar local: " + e.getMessage() + RESET);
		}
	}

	public void atualizarLocal() {
		System.out.println("\n=== Atualizar Local de Vacinação ===");
		try {
			System.out.print("ID do Local: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Novo Nome: ");
			String nome = scanner.nextLine();
			System.out.print("Novo Endereço: ");
			String endereco = scanner.nextLine();
			System.out.print("Novo Telefone: ");
			String telefone = scanner.nextLine();

			LocalVacinacao local = new LocalVacinacao(id, nome, endereco, telefone);
			if (localVacinacaoDAO.atualizar(local)) {
				System.out.println(GREEN + "✔️ Local atualizado com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ Falha ao atualizar local." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao atualizar local: " + e.getMessage() + RESET);
		}
	}

	public void excluirLocal() {
		System.out.println("\n=== Excluir Local de Vacinação ===");
		System.out.print("ID do Local: ");
		int id = scanner.nextInt();
		if (localVacinacaoDAO.excluir(id)) {
			System.out.println(GREEN + "✔️ Local excluído com sucesso!" + RESET);
		} else {
			System.out.println(RED + "❌ Falha ao excluir local." + RESET);
		}
	}

	// ============================ Menu Agentes de Saúde ============================
	public void menuAgentes() {
		int opcao;
		do {
			System.out.println("\n=== Gerenciar Agentes de Saúde ===");
			System.out.println("1. Listar Agentes");
			System.out.println("2. Cadastrar Agente");
			System.out.println("3. Adicionar Nova Vacina");
			System.out.println("4. Atualizar Estoque de Vacinas");
			System.out.println("0. Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1 -> listarAgentes();
			case 2 -> cadastrarAgente();
			case 3 -> adicionarVacina();
			case 4 -> atualizarEstoqueVacina();
			case 0 -> System.out.println("Voltando...");
			default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	public void listarAgentes() {
		System.out.println("\n=== Lista de Agentes ===");
		List<AgenteDeSaude> agentes = agenteDeSaudeDAO.listarTodos();
		agentes.forEach(System.out::println);
	}

	public void cadastrarAgente() {
		System.out.println("\n=== Cadastrar Agente de Saúde ===");
		try {
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("CPF: ");
			String cpf = scanner.nextLine();
			System.out.print("Data de Nascimento (dd/MM/yyyy): ");
			Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Senha: ");
			String senha = scanner.nextLine();

			AgenteDeSaude agente = new AgenteDeSaude(0, nome, cpf, dataNascimento, email, senha);
			if (agenteDeSaudeDAO.inserir(agente)) {
				System.out.println(GREEN + "✔️ Agente cadastrado com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ CPF já cadastrado." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao cadastrar agente: " + e.getMessage() + RESET);
		}
	}

	public void adicionarVacina() {
		System.out.println("\n=== Adicionar Nova Vacina ===");
		try {
			System.out.print("Nome da Vacina: ");
			String nome = scanner.nextLine();
			System.out.print("Fabricante: ");
			String fabricante = scanner.nextLine();
			System.out.print("Doses Recomendadas: ");
			int dosesRecomendadas = scanner.nextInt();
			System.out.print("Intervalo entre Doses (em dias): ");
			int intervalo = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Número do Lote: ");
			String numeroLote = scanner.nextLine();
			System.out.print("Validade (dd/MM/yyyy): ");
			Date validade = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
			System.out.print("Quantidade Inicial: ");
			int quantidade = scanner.nextInt();

			Lote lote = new Lote(0, numeroLote, validade, quantidade);
			Vacina vacina = new Vacina(0, nome, fabricante, dosesRecomendadas, intervalo, lote);

			if (new VacinaDAO().inserir(vacina)) {
				System.out.println(GREEN + "✔️ Vacina adicionada com sucesso!" + RESET);
			} else {
				System.out.println(RED + "❌ Falha ao adicionar vacina." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao adicionar vacina: " + e.getMessage() + RESET);
		}
	}

	public void atualizarEstoqueVacina() {
		System.out.println("\n=== Atualizar Estoque de Vacinas ===");
		try {
			System.out.print("ID da Vacina: ");
			int vacinaId = scanner.nextInt();
			System.out.print("Quantidade a Adicionar/Remover (use negativo para remover): ");
			int quantidade = scanner.nextInt();

			VacinaDAO vacinaDAO = new VacinaDAO();
			Vacina vacina = vacinaDAO.listarTodos().stream()
					.filter(v -> v.getId() == vacinaId)
					.findFirst()
					.orElse(null);

			if (vacina != null) {
				int novaQuantidade = vacina.getLote().getQuantidade() + quantidade;
				if (novaQuantidade < 0) {
					System.out.println(RED + "❌ Quantidade insuficiente em estoque!" + RESET);
				} else {
					vacina.getLote().setQuantidade(novaQuantidade);
					if (vacinaDAO.atualizarEstoqueVacina(vacinaId, novaQuantidade)) {
						System.out.println(GREEN + "✔️ Estoque atualizado para " + novaQuantidade + " unidades." + RESET);
					} else {
						System.out.println(RED + "❌ Falha ao atualizar o estoque no banco de dados." + RESET);
					}
				}
			} else {
				System.out.println(RED + "❌ Vacina não encontrada." + RESET);
			}
		} catch (Exception e) {
			System.out.println(RED + "❌ Erro ao atualizar estoque: " + e.getMessage() + RESET);
		}
	}

	// ============================ Menu Relatórios ============================
	public void menuRelatorios() {
		int opcao;
		System.out.println("\n=== Gerar Relatórios ===");
		System.out.println("1. Relatório de Vacinas Aplicadas por Agente");
		System.out.println("2. Relatório de Cidadãos Atendidos");
		System.out.println("0. Voltar");
		System.out.print("Escolha uma opção: ");
		opcao = scanner.nextInt();
		scanner.nextLine();

		switch (opcao) {
		case 1 -> gerarRelatorioVacinasPorAgente();
		case 2 -> gerarRelatorioCidadaosAtendidos();
		case 0 -> System.out.println("Voltando...");
		default -> System.out.println("Opção inválida!");
		}
	}

	public void gerarRelatorioVacinasPorAgente() {
		System.out.println("\n=== Relatório de Vacinas Aplicadas ===");
		System.out.print("ID do Agente de Saúde: ");
		int agenteId = scanner.nextInt();
		scanner.nextLine();

		AgenteDeSaude agente = agenteDeSaudeDAO.buscarPorId(agenteId);
		if (agente != null) {
			System.out.println(agente.gerarRelatorioVacinas());
		} else {
			System.out.println(RED + "❌ Agente de Saúde não encontrado." + RESET);
		}
	}

	public void gerarRelatorioCidadaosAtendidos() {
		System.out.println("\n=== Relatório de Cidadãos Atendidos ===");
		System.out.print("ID do Agente de Saúde: ");
		int agenteId = scanner.nextInt();
		scanner.nextLine();

		AgenteDeSaude agente = agenteDeSaudeDAO.buscarPorId(agenteId);
		if (agente != null) {
			System.out.println(agente.gerarRelatorioCidadaosAtendidos());
		} else {
			System.out.println(RED + "❌ Agente de Saúde não encontrado." + RESET);
		}
	}
}
