package controller;

import dao.*;
import SistemaVacinasSUS.*;
import java.util.List;

public class CidadaoController {
	private final CidadaoDAO cidadaoDAO;

	public CidadaoController() {
		this.cidadaoDAO = new CidadaoDAO();
	}

	public boolean cadastrarCidadao(Cidadao cidadao) {
		if (cidadaoDAO.verificarCpfExistente(cidadao.getCpf())) {
			System.out.println("CPF já cadastrado!");
			return false;
		}
		return cidadaoDAO.inserir(cidadao);
	}

	public List<Cidadao> listarCidadaos() {
		return cidadaoDAO.listarTodos();
	}
}