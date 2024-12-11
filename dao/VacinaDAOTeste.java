package dao;

import SistemaVacinasSUS.Lote;
import SistemaVacinasSUS.Vacina;
import java.sql.Date;

public class VacinaDAOTeste {

    public static void main(String[] args) {
        Lote lote = new Lote(1, "Lote123", new Date(System.currentTimeMillis()), 100);

        Vacina vacina = new Vacina(1, "Vacina XYZ", "Fabricante XYZ", 2, 30, lote);

        VacinaDAO vacinaDAO = new VacinaDAO();

        System.out.println("Inserindo vacina com lote...");
        boolean inserido = vacinaDAO.inserir(vacina);
        if (inserido) {
            System.out.println("Vacina inserida com sucesso!");
        } else {
            System.out.println("Erro ao inserir a vacina.");
        }

        System.out.println("\nListando todas as vacinas...");
        vacinaDAO.listarTodos().forEach(v -> {
            System.out.println("Vacina: " + v.getNome());
            System.out.println("Fabricante: " + v.getFabricante());
            System.out.println("Doses recomendadas: " + v.getDosesRecomendadas());
            System.out.println("Intervalo entre doses: " + v.getIntervaloEntreDoses());
            System.out.println("Lote: " + v.getLote().getNumeroLote());
            System.out.println("Validade: " + v.getLote().getValidade());
            System.out.println("Quantidade em estoque: " + v.getLote().getQuantidade());
            System.out.println();
        });
    }
}
