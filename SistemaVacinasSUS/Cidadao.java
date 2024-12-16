package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dao.VacinacaoDAO;

public class Cidadao extends Usuario{

	private String endereco;
	private List<Vacina> historicoVacinas = new ArrayList<>();

	public Cidadao(int id, String nome, String cpf, Date dataNascimento, String email, String senha, String endereco) {
        super(id, nome, cpf, dataNascimento, email, senha, PerfilUsuario.CIDADAO);
        this.endereco = endereco; 
        this.historicoVacinas = new ArrayList<>();
    }
	
	public List<Vacina> verificarHistorico() {
	    VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();
	    return vacinacaoDAO.buscarHistoricoVacinasPorCidadaoId(this.getId());
	}

	public List<Vacina> listarVacinasAplicadas() {
		return new ArrayList<>(historicoVacinas);
	}

	public List<Vacina> listarVacinasPendentes(List<Vacina> todasVacinas) {
		List<Vacina> pendentes = new ArrayList<>(todasVacinas);
		pendentes.removeAll(historicoVacinas);
		return pendentes;
	}

	public void registrarVacina(Vacina vacina) {
		historicoVacinas.add(vacina);
	}

	public static List<Cidadao> ordenarPorIdade(List<Cidadao> cidadaos) {
		cidadaos.sort(Comparator.comparing(Cidadao::getDataNascimento));
		return cidadaos;
	}

	public List<Vacina> consultarLocalDeVacinacao(){
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Cidadao [historicoVacinas=" + historicoVacinas + ", toString()=" + super.toString() + "]";
	}

	public String getEndereco() {
		return endereco;
	}

}
