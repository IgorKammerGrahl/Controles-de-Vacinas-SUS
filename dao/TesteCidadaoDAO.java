package dao;

import SistemaVacinasSUS.Cidadao;
import dao.CidadaoDAO; 
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TesteCidadaoDAO {
    public static void main(String[] args) {
    	LocalDate localDate = LocalDate.of(1985, 7, 20); 
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Cidadao cidadao = new Cidadao(
            0, 
            "Carlos Alberto Oliveira", 
            "49823456987", 
            dataNascimento, 
            "carlos.oliveira@email.com", 
            "senhaSegura123",
            "Avenida Brasil, 1234, Centro, São Paulo - SP" 
        );
        
        CidadaoDAO cidadaoDAO = new CidadaoDAO();

        boolean sucesso = cidadaoDAO.inserir(cidadao);

        if (sucesso) {
            System.out.println("Cidadão inserido com sucesso!");
        } else {
            System.out.println("Falha ao inserir o cidadão.");
        }

        System.out.println("Lista de cidadãos:");
        for (Cidadao c : cidadaoDAO.listarTodos()) {
            System.out.println(c);
        }
    }
}