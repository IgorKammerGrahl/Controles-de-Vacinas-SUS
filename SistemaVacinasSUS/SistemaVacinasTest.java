package SistemaVacinasSUS;

import java.util.*;

public class SistemaVacinasTest {
	public static void main(String[] args) {
        Lote loteCovid = new Lote(1, "L12345", new Date(), 50);
        Lote loteGripe = new Lote(2, "L67890", new Date(), 30);

        Vacina vacinaCovid = new Vacina(1, "COVID-19", "Pfizer", 2, 21, loteCovid);
        Vacina vacinaGripe = new Vacina(2, "Gripe", "Sanofi", 1, 0, loteGripe);

        List<Vacina> todasVacinas = new ArrayList<>();
        todasVacinas.add(vacinaCovid);
        todasVacinas.add(vacinaGripe);

        Cidadao cidadao1 = new Cidadao(1, "João Silva", "123.456.789-00", new Date(), "joao@gmail.com", "senha123");
        Cidadao cidadao2 = new Cidadao(2, "Maria Souza", "987.654.321-00", new Date(), "maria@gmail.com", "senha456");

        AgenteDeSaude agente = new AgenteDeSaude(1, "Carlos Mendes", "111.222.333-44", new Date(), "carlos@gmail.com", "senha789");

        System.out.println("Teste de autenticação de cidadão:");
        Usuario.testarAutenticacao(cidadao1, "joao@gmail.com", "senha123"); // Credenciais corretas
        Usuario.testarAutenticacao(cidadao1, "joao@gmail.com", "senhaErrada"); // Senha incorreta
        Usuario.testarAutenticacao(cidadao1, "emailErrado@gmail.com", "senha123"); // Email incorreto

        System.out.println("\nTeste de autenticação de agente de saúde:");
        Usuario.testarAutenticacao(agente, "carlos@gmail.com", "senha789"); // Credenciais corretas
        Usuario.testarAutenticacao(agente, "carlos@gmail.com", "senhaIncorreta"); // Senha incorreta
        Usuario.testarAutenticacao(agente, "outroEmail@gmail.com", "senha789"); // Email incorreto

        Vacinacao vacinacaoJoao = new Vacinacao(1, cidadao1, agente, vacinaCovid, loteCovid, new Date(), new LocalVacinacao(1, "Posto A", "Rua X", "99999999"));
        agente.registrarVacinacao(vacinacaoJoao);
        cidadao1.registrarVacina(vacinaCovid);

        Vacinacao vacinacaoMaria = new Vacinacao(2, cidadao2, agente, vacinaGripe, loteGripe, new Date(), new LocalVacinacao(2, "Posto B", "Rua Y", "88888888"));
        agente.registrarVacinacao(vacinacaoMaria);
        cidadao2.registrarVacina(vacinaGripe);

        System.out.println("\nVacinas aplicadas para João:");
        for (Vacina vacina : cidadao1.listarVacinasAplicadas()) {
            System.out.println("- " + vacina.getNome());
        }

        System.out.println("\nVacinas pendentes para João:");
        for (Vacina vacina : cidadao1.listarVacinasPendentes(todasVacinas)) {
            System.out.println("- " + vacina.getNome());
        }

        System.out.println("\nVacinas aplicadas para Maria:");
        for (Vacina vacina : cidadao2.listarVacinasAplicadas()) {
            System.out.println("- " + vacina.getNome());
        }

        System.out.println("\nVacinas pendentes para Maria:");
        for (Vacina vacina : cidadao2.listarVacinasPendentes(todasVacinas)) {
            System.out.println("- " + vacina.getNome());
        }

        System.out.println("\nRelatório Geral de Vacinas Aplicadas:");
        System.out.println(agente.gerarRelatorioVacinas());

        System.out.println("\nRelatório de Cidadãos Atendidos:");
        System.out.println(agente.gerarRelatorioCidadaosAtendidos());
    }
}

