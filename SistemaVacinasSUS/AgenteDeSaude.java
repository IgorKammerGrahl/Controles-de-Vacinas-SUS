package SistemaVacinasSUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgenteDeSaude extends Usuario {

	public AgenteDeSaude(int id, String nome, String cpf, Date dataNascimento, String email, String senha) {
		super(id, nome, cpf, dataNascimento, email, senha);
	}

	public void cadastrarVacina(Vacina vacina) {
	}

	public void atualizarDadosVacina(Vacina vacina) {
	}

	public List<Lote> visualizarLotes() {
		return new ArrayList<>(); 
	}

	@Override
	public String toString() {
		return "AgenteDeSaude [toString()=" + super.toString() + "]";
	}
}
