package dao;

import SistemaVacinasSUS.LocalVacinacao;
import SistemaVacinasSUS.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalVacinacaoDAO {
	private static final String TABELA = "local_vacinacao";

	public boolean inserir(LocalVacinacao local) {
		String sql = "INSERT INTO " + TABELA + " (nome, endereco, telefone) VALUES (?, ?, ?)";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, local.getNome());
			stmt.setString(2, local.getEndereco());
			stmt.setString(3, local.getTelefone());

			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<LocalVacinacao> listarTodos() {
		List<LocalVacinacao> locais = new ArrayList<>();
		String sql = "SELECT * FROM " + TABELA;

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String telefone = rs.getString("telefone");

				LocalVacinacao local = new LocalVacinacao(id, nome, endereco, telefone);
				locais.add(local);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locais;
	}

	public boolean atualizar(LocalVacinacao local) {
		String sql = "UPDATE " + TABELA + " SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, local.getNome());
			stmt.setString(2, local.getEndereco());
			stmt.setString(3, local.getTelefone());
			stmt.setInt(4, local.getId());

			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(int id) {
		String sql = "DELETE FROM " + TABELA + " WHERE id = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public LocalVacinacao buscarPorId(int id) {
	    String sql = "SELECT * FROM " + TABELA + " WHERE id = ?";

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String nome = rs.getString("nome");
	            String endereco = rs.getString("endereco");
	            String telefone = rs.getString("telefone");

	            return new LocalVacinacao(id, nome, endereco, telefone);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}