package resources;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import model.Ativos.Ativo;

public class SalvaAtivo {
    private static final String PATH = "resources/Arquivoscsv/";

    public static void gravarNovoAtivo(Ativo ativo) throws IOException {
        String nomeFicheiro = switch (ativo.getClass().getSimpleName()) {
            case "Acao" -> "acao.csv";
            case "FII" -> "fii.csv";
            case "Criptoativo" -> "criptoativo.csv";
            case "Stock" -> "stock.csv";
            case "Tesouro" -> "tesouro.csv";
            default -> throw new IOException("Tipo de ativo não mapeado para CSV.");
        };

        // O 'true' ativa o modo de adição (não sobrescreve o ficheiro)
        try (PrintWriter out = new PrintWriter(new FileWriter(PATH + nomeFicheiro, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n")
              .append(ativo.getTicker()).append(";")
              .append(ativo.getNome()).append(";")
              .append(ativo.getPrecoAtual()).append(";")
              .append(ativo.isQualificado() ? "1" : "0");
            
            out.println(sb.toString());
        }
    }
}