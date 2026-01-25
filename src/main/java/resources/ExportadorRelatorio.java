package resources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import model.Investidores.Investidor;
import model.Investidores.PessoaFisica;
import model.Investidores.Institucional;
import model.ItemCarteira;

public class ExportadorRelatorio {

    public static void gerarArquivoJson(Investidor inv) throws IOException {
        String nomeArquivo = "relatorio_" + inv.getDocumento() + ".json";

        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            
            // --- DADOS CADASTRAIS ---
            sb.append("  \"nome\": \"").append(inv.getNome()).append("\",\n");
            sb.append("  \"documento\": \"").append(inv.getDocumento()).append("\",\n");
            sb.append("  \"telefone\": \"").append(inv.getTelefone()).append("\",\n"); 
            sb.append("  \"dataNascimento\": \"").append(inv.getDataNascimento()).append("\",\n");
            sb.append("  \"endereco\": \"").append(inv.getEndereco()).append("\",\n");
            sb.append(String.format(Locale.US, "  \"patrimonioTotalDeclarado\": %.2f,\n", inv.getPatrimonioTotal()));

            if (inv instanceof PessoaFisica pf) {
                sb.append("  \"tipo\": \"Pessoa Física\",\n");
                sb.append("  \"perfil\": \"").append(pf.getPerfil()).append("\",\n");
            } else if (inv instanceof Institucional inst) {
                sb.append("  \"tipo\": \"Institucional\",\n");
                sb.append("  \"razaoSocial\": \"").append(inst.getRazaoSocial()).append("\",\n");
            }

            // --- CÁLCULOS FINANCEIROS ---
            double valorTotalAtual = inv.getCarteira().getValorTotalEmReais();
 
            double valorTotalGasto = 0;
            for (ItemCarteira item : inv.getCarteira().getItens()) {
                valorTotalGasto += item.getQuantidade() * item.getPrecoMedio();
            }
            
            double pctRendaFixa = inv.getCarteira().getPercentualRendaFixa();
            double pctRendaVariavel = inv.getCarteira().getPercentualRendaVariavel();
            double pctNacional = inv.getCarteira().getPercentualNacional();
            double pctInternacional = inv.getCarteira().getPercentualInternacional();

            sb.append(String.format(Locale.US, "  \"valorTotalGasto\": %.2f,\n", valorTotalGasto));
            sb.append(String.format(Locale.US, "  \"valorTotalAtual\": %.2f,\n", valorTotalAtual));
            
            sb.append("  \"alocacao\": {\n");
            sb.append(String.format(Locale.US, "    \"rendaFixa\": %.2f,\n", pctRendaFixa));
            sb.append(String.format(Locale.US, "    \"rendaVariavel\": %.2f\n", pctRendaVariavel));
            sb.append("  },\n");

            sb.append("  \"nacionalidade\": {\n");
            sb.append(String.format(Locale.US, "    \"nacional\": %.2f,\n", pctNacional));
            sb.append(String.format(Locale.US, "    \"internacional\": %.2f\n", pctInternacional));
            sb.append("  },\n");

            // --- ITENS DA CARTEIRA ---
            sb.append("  \"carteira\": [\n");
            var itens = inv.getCarteira().getItens();
            for (int i = 0; i < itens.size(); i++) {
                ItemCarteira item = itens.get(i);
                sb.append("    {\n");
                sb.append("      \"ticker\": \"").append(item.getAtivo().getTicker()).append("\",\n");
                sb.append(String.format(Locale.US, "      \"quantidade\": %.4f,\n", item.getQuantidade()));
                sb.append(String.format(Locale.US, "      \"precoMedio\": %.2f\n", item.getPrecoMedio()));
                sb.append("    }");
                
                if (i < itens.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("  ]\n}");

            out.print(sb.toString());
        }
    }
}