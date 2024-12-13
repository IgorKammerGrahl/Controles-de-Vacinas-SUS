package dao;

import SistemaVacinasSUS.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacinacaoDAO {
	private Connection connection;

	public VacinacaoDAO() {
		this.connection = Conexao.conectar(); 
	}

	public Cidadao buscarCidadaoPorId(int id) {
		try {
			String query = "SELECT * FROM cidadao WHERE idcidadao = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
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

	public AgenteDeSaude buscarAgentePorId(int id) {
		try {
			String query = "SELECT * FROM agente_de_saude WHERE idagente = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new AgenteDeSaude(
						rs.getInt("idagente"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getDate("data_nascimento"),
						rs.getString("email"),
						rs.getString("senha")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LocalVacinacao buscarLocalPorId(int id) {
		try {
			String query = "SELECT * FROM local_vacinacao WHERE id = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new LocalVacinacao(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("endereco"),
						rs.getString("telefone")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Vacina> buscarVacinasPorIds(List<Integer> ids) {
		List<Vacina> vacinas = new ArrayList<>();
		try {
			String query = "SELECT * FROM vacina WHERE id IN (" + ids.stream().map(i -> "?").reduce((a, b) -> a + ", " + b).orElse("") + ")";
			PreparedStatement stmt = connection.prepareStatement(query);

			for (int i = 0; i < ids.size(); i++) {
				stmt.setInt(i + 1, ids.get(i));
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				vacinas.add(new Vacina(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("fabricante"),
						rs.getInt("doses_recomendadas"),
						rs.getInt("intervalo_entre_doses"),
						new Lote(
								rs.getInt("id"),
								rs.getString("numero_lote"),
								rs.getDate("validade"),
								rs.getInt("quantidade")
								)
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vacinas;
	}

	public void salvar(Vacinacao vacinacao) {
		String insertVacinacao = "INSERT INTO vacinacao (cidadao_id, agente_id, local_id, data) VALUES (?, ?, ?, ?)";
		String insertVacinas = "INSERT INTO vacinacao_vacina (vacinacao_id, vacina_id) VALUES (?, ?)"; 

		try {
			connection.setAutoCommit(false);

			PreparedStatement stmtVacinacao = connection.prepareStatement(insertVacinacao, Statement.RETURN_GENERATED_KEYS);
			stmtVacinacao.setInt(1, vacinacao.getCidadao().getId());  
			stmtVacinacao.setInt(2, vacinacao.getAgente().getId());  
			stmtVacinacao.setInt(3, vacinacao.getLocal().getId());  
			stmtVacinacao.setDate(4, new java.sql.Date(vacinacao.getData().getTime())); 

			int rowsInserted = stmtVacinacao.executeUpdate();
			if (rowsInserted > 0) {
				ResultSet generatedKeys = stmtVacinacao.getGeneratedKeys();
				if (generatedKeys.next()) {
					int vacinacaoId = generatedKeys.getInt(1); 

					PreparedStatement stmtVacina = connection.prepareStatement(insertVacinas);
					for (Vacina vacina : vacinacao.getVacinas()) {
						stmtVacina.setInt(1, vacinacaoId);
						stmtVacina.setInt(2, vacina.getId()); 
						stmtVacina.addBatch();  
					}
					stmtVacina.executeBatch(); 

					connection.commit();
					System.out.println("Vacinação salva com sucesso! Detalhes: " + vacinacao);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		} finally {
			try {
				connection.setAutoCommit(true); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Vacinacao> listarTodos() {
		List<Vacinacao> vacinacoes = new ArrayList<>();
		try {
			String query = "SELECT * FROM vacinacao"; 
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int vacinacaoId = rs.getInt("id");
				int cidadaoId = rs.getInt("cidadao_id");
				int agenteId = rs.getInt("agente_id");
				int localVacinacaoId = rs.getInt("local_id"); 

				Cidadao cidadao = buscarCidadaoPorId(cidadaoId);
				AgenteDeSaude agente = buscarAgentePorId(agenteId);
				LocalVacinacao localVacinacao = buscarLocalPorId(localVacinacaoId);

				List<Vacina> vacinas = buscarVacinasPorVacinacaoId(vacinacaoId);

				Vacinacao vacinacao = new Vacinacao(vacinacaoId, cidadao, agente, vacinas, rs.getDate("data"), localVacinacao);

				vacinacoes.add(vacinacao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vacinacoes;
	}

	public List<Vacina> buscarVacinasPorVacinacaoId(int vacinacaoId) {
		List<Vacina> vacinas = new ArrayList<>();
		String sql = "SELECT v.id AS vacina_id, v.nome, v.fabricante, v.doses_recomendadas, v.intervalo_entre_doses, " +
				"l.id AS lote_id, l.numero_lote, l.validade, l.quantidade " +
				"FROM vacina v " +
				"JOIN lote l ON v.numero_lote = l.numero_lote " +
				"JOIN vacinacao_vacina vv ON vv.vacina_id = v.id " +
				"WHERE vv.vacinacao_id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, vacinacaoId);  
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int vacinaId = rs.getInt("vacina_id");
				String nome = rs.getString("nome");
				String fabricante = rs.getString("fabricante");
				int dosesRecomendadas = rs.getInt("doses_recomendadas");
				int intervaloEntreDoses = rs.getInt("intervalo_entre_doses");

				int loteId = rs.getInt("lote_id");
				String numeroLote = rs.getString("numero_lote");
				Date validade = rs.getDate("validade");
				int quantidade = rs.getInt("quantidade");

				Lote lote = new Lote(loteId, numeroLote, validade, quantidade);

				Vacina vacina = new Vacina(vacinaId, nome, fabricante, intervaloEntreDoses, dosesRecomendadas, lote);
				vacinas.add(vacina);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vacinas;
	}
}
