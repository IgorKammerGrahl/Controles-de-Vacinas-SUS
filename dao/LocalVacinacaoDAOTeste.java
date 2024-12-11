package dao;

import SistemaVacinasSUS.LocalVacinacao;

public class LocalVacinacaoDAOTeste {

	public static void main(String[] args) {
		LocalVacinacaoDAO dao = new LocalVacinacaoDAO();

		System.out.println("Inserindo local de vacinação...");
		LocalVacinacao local1 = new LocalVacinacao(0, "Posto Central", "Rua Principal, 123", "1234-5678");
		if (dao.inserir(local1)) {
			System.out.println("Local inserido com sucesso!");
		} else {
			System.out.println("Erro ao inserir local.");
		}

		System.out.println("\nListando todos os locais...");
		dao.listarTodos().forEach(local -> {
			System.out.println(local);
		});

		System.out.println("\nAtualizando o local de vacinação...");
		LocalVacinacao localAtualizado = new LocalVacinacao(1, "Posto Atualizado", "Rua Nova, 456", "8765-4321");
		if (dao.atualizar(localAtualizado)) {
			System.out.println("Local atualizado com sucesso!");
		} else {
			System.out.println("Erro ao atualizar local.");
		}

		System.out.println("\nBuscando um local pelo ID...");
		LocalVacinacao buscado = dao.buscarPorId(1); 
		if (buscado != null) {
			System.out.println("Local encontrado: " + buscado);
		} else {
			System.out.println("Local não encontrado.");
		}

		System.out.println("\nExcluindo um local de vacinação...");
		if (dao.excluir(1)) { 
			System.out.println("Local excluído com sucesso!");
		} else {
			System.out.println("Erro ao excluir local.");
		}

		System.out.println("\nListando todos os locais após exclusão...");
		dao.listarTodos().forEach(local -> {
			System.out.println(local);
		});
	}
}
