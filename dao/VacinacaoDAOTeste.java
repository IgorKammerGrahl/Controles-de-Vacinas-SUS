package dao;

import SistemaVacinasSUS.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VacinacaoDAOTeste {
    public static void main(String[] args) {
        VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();

        System.out.println("Buscando cidadão...");
        Cidadao cidadao = vacinacaoDAO.buscarCidadaoPorId(1); 
        System.out.println("Cidadão encontrado: " + cidadao);

        System.out.println("Buscando agente de saúde...");
        AgenteDeSaude agente = vacinacaoDAO.buscarAgentePorId(1); 
        System.out.println("Agente de saúde encontrado: " + agente);

        System.out.println("Buscando local de vacinação...");
        LocalVacinacao local = vacinacaoDAO.buscarLocalPorId(2); 
        System.out.println("Local de vacinação encontrado: " + local);

        System.out.println("Buscando vacinas...");
        List<Vacina> vacinas = vacinacaoDAO.buscarVacinasPorIds(List.of(4)); 
        System.out.println("Vacinas encontradas: " + vacinas);

        System.out.println("Criando vacinação...");
        Vacinacao vacinacao = new Vacinacao(0, cidadao, agente, vacinas, new Date(), local);
        vacinacaoDAO.salvar(vacinacao);
        System.out.println("Vacinação criada com sucesso!");

        System.out.println("Listando todas as vacinações...");
        List<Vacinacao> vacinacoes = vacinacaoDAO.listarTodos();
        for (Vacinacao v : vacinacoes) {
            System.out.println(v);
        }
    }
}
