package SistemaVacinasSUS;

import java.util.*;

public class SistemaVacinasTest {
	public static void main(String[] args) {
        Lote loteCovid = new Lote(1, "L12345", new Date(), 50);
        Lote loteGripe = new Lote(2, "L67890", new Date(), 30);
        Vacina vacinaCovid = new Vacina(1, "COVID-19", "Pfizer", 2, 21, loteCovid);
        Vacina vacinaGripe = new Vacina(2, "Gripe", "Sanofi", 1, 0, loteGripe);

        //Cidadao cidadao1 = new Cidadao(1, "João Silva", "123.456.789-00", new Date(), "joao@gmail.com", "senha123");

        LocalVacinacao local = new LocalVacinacao(1, "Posto de Saúde", "Rua 123", "99999-999");

        AgenteDeSaude agente = new AgenteDeSaude(1, "Carlos Mendes", "111.222.333-44", new Date(), "carlos@gmail.com", "senha789");

        List<Vacina> vacinasParaHoje = List.of(vacinaCovid, vacinaGripe);
        //Vacinacao vacinacao = new Vacinacao(1, cidadao1, agente, vacinasParaHoje, new Date(), local);

        //agente.registrarVacinacao(vacinacao);

        System.out.println("\nRelatório Geral de Vacinas Aplicadas:");
        System.out.println(agente.gerarRelatorioVacinas());
    }
}

