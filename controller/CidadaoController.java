package controller;

import SistemaVacinasSUS.Cidadao;

import java.util.List;

import dao.CidadaoDAO;

public class CidadaoController {
	private CidadaoDAO dao;

	public CidadaoController() {
		this.dao = new CidadaoDAO();
	}

	public boolean cadastrarCidadao(Cidadao cidadao) {
		return dao.inserir(cidadao);
	}

	public List<Cidadao> listarCidadaos() {
		return dao.listarTodos();
	}
}
