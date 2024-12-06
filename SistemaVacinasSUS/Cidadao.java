package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cidadao extends Usuario{
	
	private List<Vacina> historicoVacinas = new ArrayList<>();
	
	public Cidadao(int id, String nome, String cpf, Date dataNascimento, String email, String senha) {
        super(id, nome, cpf, dataNascimento, email, senha);
        this.historicoVacinas = new ArrayList<>();
    }
	
	public List<Vacina> verificarHistorico(){
		return historicoVacinas;
	}
	
	public List<Vacina> consultarVacinasRecomendadas(){
		return new ArrayList<>();
	}

	public List<Vacina> consultarLocalDeVacinacao(){
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Cidadao [historicoVacinas=" + historicoVacinas + ", toString()=" + super.toString() + "]";
	}
	
}
