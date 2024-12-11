package SistemaVacinasSUS;

import dao.AgendamentoDAO;
import dao.CidadaoDAO;
import dao.LocalVacinacaoDAO;
import dao.VacinaDAO;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class AgendamentoDAOTeste {

    public static void main(String[] args) {
        // Instâncias de DAOs
        CidadaoDAO cidadaoDAO = new CidadaoDAO();
        LocalVacinacaoDAO localDAO = new LocalVacinacaoDAO();
        VacinaDAO vacinaDAO = new VacinaDAO();
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        // Buscar ou criar um cidadão para o agendamento
        System.out.println("Buscando cidadão...");
        Cidadao cidadao = cidadaoDAO.buscarPorId(1); // Substituir com um ID válido do banco
        if (cidadao == null) {
            cidadao = new Cidadao(0, "João da Silva", "123.456.789-00", new Date(System.currentTimeMillis()), "joao@email.com", "senha123", "Rua Central, 123");
            cidadaoDAO.inserir(cidadao);
            cidadao = cidadaoDAO.buscarPorId(1); // Rebuscar para obter o ID
        }
        System.out.println("Cidadão encontrado/criado: " + cidadao);

        // Buscar ou criar um local de vacinação
        System.out.println("Buscando local de vacinação...");
        LocalVacinacao local = localDAO.buscarPorId(1); // Substituir com um ID válido do banco
        if (local == null) {
            local = new LocalVacinacao(0, "Posto Central", "Rua Principal, 123", "1234-5678");
            localDAO.inserir(local);
            local = localDAO.buscarPorId(1); // Rebuscar para obter o ID
        }
        System.out.println("Local de vacinação encontrado/criado: " + local);

        // Buscar vacinas para o agendamento
        System.out.println("Buscando vacinas...");
        List<Vacina> vacinas = vacinaDAO.listarTodos();
        if (vacinas.isEmpty()) {
            Vacina vacina1 = new Vacina(0, "Vacina COVID-19", "Fabricante X", 2, 30, null);
            Vacina vacina2 = new Vacina(0, "Vacina Gripe", "Fabricante Y", 1, 0, null);
            vacinaDAO.inserir(vacina1);
            vacinaDAO.inserir(vacina2);
            vacinas = vacinaDAO.listarTodos();
        }
        System.out.println("Vacinas encontradas: " + vacinas.size());

        // Criar um novo agendamento
        System.out.println("Criando agendamento...");
        Agendamento agendamento = new Agendamento(
            0,
            cidadao,
            vacinas,
            new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)), // Agendamento para 7 dias no futuro
            local
        );

        if (agendamentoDAO.inserir(agendamento)) {
            System.out.println("Agendamento criado com sucesso!");
        } else {
            System.out.println("Erro ao criar agendamento.");
        }

        // Listar todos os agendamentos
        System.out.println("\nListando todos os agendamentos...");
        agendamentoDAO.listarTodos().forEach(a -> {
            System.out.println(a.visualizarAgendamento());
        });

        // Confirmar o agendamento
        System.out.println("\nConfirmando o agendamento...");
        agendamentoDAO.atualizarStatus(1, Agendamento.StatusAgendamento.CONFIRMADO);

        // Excluir o agendamento
        System.out.println("\nExcluindo o agendamento...");
        if (agendamentoDAO.excluir(1)) {
            System.out.println("Agendamento excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir agendamento.");
        }

        // Listar novamente para verificar exclusão
        System.out.println("\nListando agendamentos após exclusão...");
        agendamentoDAO.listarTodos().forEach(a -> {
            System.out.println(a.visualizarAgendamento());
        });
    }
}
