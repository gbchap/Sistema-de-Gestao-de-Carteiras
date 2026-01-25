package resources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import model.Investidores.Investidor;
import model.Investidores.PessoaFisica;
import model.Investidores.Institucional;
import model.ItemCarteira;

/**
 * Classe responsável por exportar os dados do investidor e sua carteira
 * para um arquivo físico, atendendo ao requisito de persistência do trabalho.
 */
public class ExportadorRelatorio {

    public static void gerarArquivoJson(Investidor inv) throws IOException {
        // Nome do arquivo baseado no documento (CPF/CNPJ) para ser único
        String nomeArquivo = "relatorio_" + inv.getDocumento() + ".json";

        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"nome\": \"").append(inv.getNome()).append("\",\n");
            sb.append("  \"documento\": \"").append(inv.getDocumento()).append("\",\n");
            sb.append("  \"telefone\": \"").append(inv.getTelefone()).append("\",\n"); 
            sb.append("  \"dataNascimento\": \"").append(inv.getDataNascimento()).append("\",\n");
            sb.append("  \"endereco\": \"").append(inv.getEndereco()).append("\",\n");
            sb.append("  \"patrimonioTotal\": ").append(inv.getPatrimonioTotal()).append(",\n");

            // identifica se é PF ou PJ para campos específicos
            if (inv instanceof PessoaFisica pf) {
                sb.append("  \"tipo\": \"Pessoa Física\",\n");
                sb.append("  \"perfil\": \"").append(pf.getPerfil()).append("\",\n");
            } else if (inv instanceof Institucional inst) {
                sb.append("  \"tipo\": \"Institucional\",\n");
                sb.append("  \"razaoSocial\": \"").append(inst.getRazaoSocial()).append("\",\n");
            }

            // Início da lista da Carteira
            sb.append("  \"carteira\": [\n");
            var itens = inv.getCarteira().getItens();
            for (int i = 0; i < itens.size(); i++) {
                ItemCarteira item = itens.get(i);
                sb.append("    {\n");
                sb.append("      \"ticker\": \"").append(item.getAtivo().getTicker()).append("\",\n");
                sb.append("      \"quantidade\": ").append(item.getQuantidade()).append(",\n");
                sb.append("      \"precoMedio\": ").append(item.getPrecoMedio()).append("\n");
                sb.append("    }");
                
                // Só adiciona vírgula se não for o último item (regra do JSON)
                if (i < itens.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("  ]\n}");

            // Escreve tudo de uma vez no arquivo
            out.print(sb.toString());
        }
    }
}