package dao;

import SistemaVacinasSUS.Agendamento;
import SistemaVacinasSUS.Cidadao;
import SistemaVacinasSUS.LocalVacinacao;
import SistemaVacinasSUS.Vacina;
import SistemaVacinasSUS.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {
	private static final String TABELA = "agendamento";
	private static final String TABELA_INTERMEDIARIA = "agendamento_vacina";

	public boolean inserir(Agendamento agendamento) {
		String sqlAgendamento = "INSERT INTO agendamento (idcidadao, local_id, data_agendamento, status) VALUES (?, ?, ?, ?)";
		String sqlVacinas = "INSERT INTO agendamento_vacina (agendamento_id, vacina_id) VALUES (?, ?)";

		try (Connection conn = Conexao.conectar()) {
			conn.setAutoCommit(false);

			try (PreparedStatement stmtAgendamento = conn.prepareStatement(sqlAgendamento, Statement.RETURN_GENERATED_KEYS)) {
				stmtAgendamento.setInt(1, agendamento.getCidadao().getId());
				stmtAgendamento.setInt(2, agendamento.getLocal().getId());
				stmtAgendamento.setDate(3, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
				stmtAgendamento.setString(4, agendamento.getStatus().toString());

				int rowsAffected = stmtAgendamento.executeUpdate();
				System.out.println("Linhas afetadas na tabela agendamento: " + rowsAffected);

				try (ResultSet generatedKeys = stmtAgendamento.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int agendamentoId = generatedKeys.getInt(1);
						System.out.println("ID do agendamento criado: " + agendamentoId);

						try (PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas)) {
							for (Vacina vacina : agendamento.getVacinas()) {
								stmtVacinas.setInt(1, agendamentoId);
								stmtVacinas.setInt(2, vacina.getId());
								stmtVacinas.executeUpdate();
							}
						}
					}
				}
			}
			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Agendamento> listarTodos() {
		List<Agendamento> agendamentos = new ArrayList<>();
		String sql = """
				    SELECT a.id, a.data_agendamento, a.status, 
				           c.idcidadao AS cidadao_id, c.nome AS cidadao_nome,
				           l.id AS local_id, l.nome AS local_nome
				    FROM agendamento a
				    JOIN cidadao c ON a.idcidadao = c.idcidadao
				    JOIN local_vacinacao l ON a.local_id = l.id
				""";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				Date dataAgendamento = rs.getDate("data_agendamento");
				Agendamento.StatusAgendamento status = Agendamento.StatusAgendamento.valueOf(rs.getString("status"));

				CidadaoDAO cidadaoDAO = new CidadaoDAO();
				Cidadao cidadao = cidadaoDAO.buscarPorId(rs.getInt("cidadao_id"));
				if (cidadao == null) {
					throw new IllegalStateException("Cidadão não encontrado no banco de dados.");
				}
				LocalVacinacao local = new LocalVacinacao(rs.getInt("local_id"), rs.getString("local_nome"), null, null);

				List<Vacina> vacinas = listarVacinasPorAgendamento(id);
				Agendamento agendamento = new Agendamento(id, cidadao, vacinas, dataAgendamento, local);
				agendamento.setStatus(status);

				agendamentos.add(agendamento);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agendamentos;
	}

	private List<Vacina> listarVacinasPorAgendamento(int agendamentoId) {
		List<Vacina> vacinas = new ArrayList<>();
		String sql = """
				    SELECT v.id, v.nome, v.fabricante, v.doses_recomendadas, v.intervalo_entre_doses
				    FROM vacina v
				    JOIN agendamento_vacina av ON v.id = av.vacina_id
				    WHERE av.agendamento_id = ?
				""";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, agendamentoId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Vacina vacina = new Vacina(
							rs.getInt("id"),
							rs.getString("nome"),
							rs.getString("fabricante"),
							rs.getInt("doses_recomendadas"),
							rs.getInt("intervalo_entre_doses"),
							null
							);
					vacinas.add(vacina);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vacinas;
	}

	public boolean atualizarStatus(int agendamentoId, Agendamento.StatusAgendamento status) {
		String sql = "UPDATE " + TABELA + " SET status = ? WHERE id = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, status.toString());
			stmt.setInt(2, agendamentoId);

			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(int agendamentoId) {
		String sql = "DELETE FROM " + TABELA + " WHERE id = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, agendamentoId);
			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
