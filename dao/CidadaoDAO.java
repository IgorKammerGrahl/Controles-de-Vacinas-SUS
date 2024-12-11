package dao;

import SistemaVacinasSUS.Cidadao;
import SistemaVacinasSUS.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadaoDAO {
	private static final String TABELA = "cidadao";

	public boolean inserir(Cidadao cidadao) {
		if (verificarCpfExistente(cidadao.getCpf())) {
			System.out.println("CPF já cadastrado!");
			return false;
		}
		String sql = "INSERT INTO " + TABELA + " (nome, cpf, data_nascimento, email, senha, endereco) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cidadao.getNome());
			ps.setString(2, cidadao.getCpf());
			ps.setDate(3, new java.sql.Date(cidadao.getDataNascimento().getTime()));
			ps.setString(4, cidadao.getEmail());
			ps.setString(5, cidadao.getSenha());
			ps.setString(6, cidadao.getEndereco());
			ps.executeUpdate();
			System.out.println("Cidadão inserido com sucesso!");
			return true; 
		} catch (SQLException e) {
			System.out.println("Falha ao inserir o cidadão.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarCpfExistente(String cpf) {
		String sql = "SELECT COUNT(*) FROM " + TABELA + " WHERE cpf = ?";
		try (Connection conn = Conexao.conectar(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;  
	}

	public List<Cidadao> listarTodos() {
		List<Cidadao> cidadaos = new ArrayList<>();
		try (Connection conn = Conexao.conectar()) {
			String sql = "SELECT * FROM " + TABELA;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cidadao cidadao = new Cidadao(
						rs.getInt("idcidadao"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getDate("data_nascimento"),
						rs.getString("email"),
						rs.getString("senha"),
						rs.getString("endereco")
						);
				cidadaos.add(cidadao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cidadaos;
	}

	public Cidadao buscarPorId(int id) {
		String sql = "SELECT * FROM cidadao WHERE idcidadao = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Cidadao(
						rs.getInt("idcidadao"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getDate("data_nascimento"),
						rs.getString("email"),
						rs.getString("senha"),
						rs.getString("endereco")
						);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
