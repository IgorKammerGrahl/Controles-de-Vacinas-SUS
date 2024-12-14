package dao;

import SistemaVacinasSUS.Vacina;
import SistemaVacinasSUS.Lote;
import SistemaVacinasSUS.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VacinaDAO {
	private static final String TABELA = "vacina";

	public boolean inserir(Vacina vacina) {
		String sql = """
				    INSERT INTO vacina 
				    (nome, fabricante, doses_recomendadas, intervalo_entre_doses, numero_lote, validade, quantidade) 
				    VALUES (?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, vacina.getNome());
			stmt.setString(2, vacina.getFabricante());
			stmt.setInt(3, vacina.getDosesRecomendadas());
			stmt.setInt(4, vacina.getIntervaloEntreDoses());

			Lote lote = vacina.getLote();
			stmt.setString(5, lote.getNumeroLote());
			stmt.setDate(6, new java.sql.Date(lote.getValidade().getTime()));
			stmt.setInt(7, lote.getQuantidade());

			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Vacina> listarTodos() {
		List<Vacina> vacinas = new ArrayList<>();
		String sql = """
				    SELECT id, nome, fabricante, doses_recomendadas, intervalo_entre_doses, 
				           numero_lote, validade, quantidade 
				    FROM vacina
				""";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String fabricante = rs.getString("fabricante");
				int dosesRecomendadas = rs.getInt("doses_recomendadas");
				int intervaloEntreDoses = rs.getInt("intervalo_entre_doses");
				String numeroLote = rs.getString("numero_lote");
				Date validade = rs.getDate("validade");
				int quantidade = rs.getInt("quantidade");
				Lote lote = new Lote(0, numeroLote, validade, quantidade);

				Vacina vacina = new Vacina(id, nome, fabricante, dosesRecomendadas, intervaloEntreDoses, lote);
				vacinas.add(vacina);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vacinas;
	}

	public boolean verificarNomeVacina(String nome) {
		String sql = "SELECT COUNT(*) FROM " + TABELA + " WHERE nome = ?";

		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Vacina> buscarVacinasPorIds(List<Integer> vacinasIds) {
	    List<Vacina> vacinas = new ArrayList<>();
	    if (vacinasIds == null || vacinasIds.isEmpty()) {
	        return vacinas;
	    }

	    String sql = """
	            SELECT id, nome, fabricante, doses_recomendadas, intervalo_entre_doses,
	                   numero_lote, validade, quantidade
	            FROM vacina
	            WHERE id IN (%s)
	            """.formatted(vacinasIds.stream().map(id -> "?").collect(Collectors.joining(", ")));

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        for (int i = 0; i < vacinasIds.size(); i++) {
	            stmt.setInt(i + 1, vacinasIds.get(i));
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String nome = rs.getString("nome");
	                String fabricante = rs.getString("fabricante");
	                int dosesRecomendadas = rs.getInt("doses_recomendadas");
	                int intervaloEntreDoses = rs.getInt("intervalo_entre_doses");
	                String numeroLote = rs.getString("numero_lote");
	                Date validade = rs.getDate("validade");
	                int quantidade = rs.getInt("quantidade");

	                Lote lote = new Lote(0, numeroLote, validade, quantidade);
	                Vacina vacina = new Vacina(id, nome, fabricante, dosesRecomendadas, intervaloEntreDoses, lote);
	                vacinas.add(vacina);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return vacinas;
	}
}