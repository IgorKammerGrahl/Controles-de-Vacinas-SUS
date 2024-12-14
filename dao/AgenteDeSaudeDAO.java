package dao;

import SistemaVacinasSUS.AgenteDeSaude;
import SistemaVacinasSUS.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDeSaudeDAO {

	public boolean inserir(AgenteDeSaude agenteDeSaude) {
		String sql = "INSERT INTO agente_de_saude (nome, cpf, data_nascimento, email, senha) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, agenteDeSaude.getNome());
			stmt.setString(2, agenteDeSaude.getCpf());
			stmt.setDate(3, new java.sql.Date(agenteDeSaude.getDataNascimento().getTime()));
			stmt.setString(4, agenteDeSaude.getEmail());
			stmt.setString(5, agenteDeSaude.getSenha());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<AgenteDeSaude> listarTodos() {
		String sql = "SELECT * FROM agente_de_saude";
		List<AgenteDeSaude> agentes = new ArrayList<>();
		try (Connection conn = Conexao.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("idagente");
				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");
				Date dataNascimento = rs.getDate("data_nascimento");
				String email = rs.getString("email");
				String senha = rs.getString("senha");

				AgenteDeSaude agente = new AgenteDeSaude(id, nome, cpf, dataNascimento, email, senha);
				agentes.add(agente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agentes;
	}

	public boolean verificarCpf(String cpf) {
		String sql = "SELECT * FROM agente_de_saude WHERE cpf = ?";
		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();

			return rs.next(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public AgenteDeSaude buscarPorId(int id) {
		String sql = "SELECT * FROM agente_de_saude WHERE idagente = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");
				Date dataNascimento = rs.getDate("data_nascimento");
				String email = rs.getString("email");
				String senha = rs.getString("senha");

				return new AgenteDeSaude(id, nome, cpf, dataNascimento, email, senha);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; 
	}

}
