package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Cidadao extends Usuario{

	private List<Vacina> historicoVacinas = new ArrayList<>();

	public Cidadao(int id, String nome, String cpf, Date dataNascimento, String email, String senha) {
		super(id, nome, cpf, dataNascimento, email, senha, PerfilUsuario.CIDADAO);
		this.historicoVacinas = new ArrayList<>();
	}

	public List<Vacina> verificarHistorico(){
		return historicoVacinas;
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

}
