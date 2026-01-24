package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Investidores.Investidor;
import model.*;

public class CarregarCSV {

    // Ajusta este caminho conforme a estrutura do nosso VS Code
    private static final String BASE_PATH = "resources/Arquivoscsv/";
    /**
     * Método mestre que centraliza a carga de todos os ficheiros.
     * É este método que o SistemaGestao chama no início.
     */
    public static List<Ativo> carregarTodosAtivos() {
        List<Ativo> listaCompleta = new ArrayList<>();

        // Adiciona os ativos de cada categoria à lista principal
        listaCompleta.addAll(lerAcoes(BASE_PATH + "acao.csv"));
        listaCompleta.addAll(lerFIIs(BASE_PATH + "fii.csv"));
        listaCompleta.addAll(lerCriptos(BASE_PATH + "criptoativo.csv"));
        listaCompleta.addAll(lerStocks(BASE_PATH + "stock.csv"));
        listaCompleta.addAll(lerTesouro(BASE_PATH + "tesouro.csv"));

        return listaCompleta;
    }

    private static List<Acao> lerAcoes(String caminho) {
        List<Acao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); // Pular cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                // Ticker;Nome;Preço;Qualificado
                String ticker = dados[0];
                String nome = dados[1];
                double preco = Double.parseDouble(dados[2]);
                boolean qualificado = dados[3].equals("1");
                lista.add(new Acao(nome, ticker, preco, qualificado));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler acao.csv: " + e.getMessage());
        }
        return lista;
    }

    private static List<FII> lerFIIs(String caminho) {
        List<FII> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
               // uma validação doida aqui pra ler direito pq nao parava de cair no erro de multiple points
                String[] d = linha.split(";");

                if (d.length < 6 || d[3].equals("-")) continue; 

                try {
                    // TRATAMENTO DEFINITIVO:
                    // 1. d[3].replace(".", "") -> Remove o ponto de milhar (ex: 1.051.35 vira 1051.35)
                    // 2. .replace(",", ".") -> Caso exista vírgula, troca por ponto para o padrão Java
                    String precoTratado = d[3].replace(".", "").replace(",", ".");
                    double preco = Double.parseDouble(precoTratado);

                    // Repetir o tratamento para dividendos e taxas por segurança
                    double dividendo = Double.parseDouble(d[4].replace(".", "").replace(",", "."));
                    double taxa = Double.parseDouble(d[5].replace(".", "").replace(",", "."));

                    lista.add(new FII(d[1], d[0], preco, false, d[2], dividendo, taxa));
                } catch (NumberFormatException e) {
                    // aqui o sistema avida qual linha deu problema
                    System.err.println("Falha ao converter valores na linha: " + linha);
                } 
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler fii.csv: " + e.getMessage());
        }
        return lista;
    }

    private static List<Criptoativo> lerCriptos(String caminho) {
        List<Criptoativo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço(USD);Consenso;Max
                
                // TRATAMENTO DO ERRO "Index out of bounds":
                // Verificamos se a coluna existe antes de tentar ler
                String ticker = d[0];
                String nome = d[1];
                double preco = Double.parseDouble(d[2]);
                
                // Se o tamanho do array d for maior que 3, a coluna existe; senão, usamos um padrão
                String consenso = (d.length > 3) ? d[3] : "N/A";
                String qtdMax = (d.length > 4) ? d[4] : "Ilimitado";

                lista.add(new Criptoativo(nome, ticker, preco, false, consenso, qtdMax, 5.39));
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler criptoativo.csv: " + e.getMessage());
        }
        return lista;
    }

    private static List<Stock> lerStocks(String caminho) {
        List<Stock> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço(USD);Bolsa;Setor
                lista.add(new Stock(d[1], d[0], Double.parseDouble(d[2]), false, d[3], d[4], 5.39));
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler stock.csv: " + e.getMessage());
        }
        return lista;
    }

    private static List<Tesouro> lerTesouro(String caminho) {
        List<Tesouro> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço;Rendimento;Vencimento
                lista.add(new Tesouro(d[1], d[0], Double.parseDouble(d[2]), false, d[3], d[4]));
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler tesouro.csv: " + e.getMessage());
        }
        return lista;
        }
    public static List<Investidor> lerInvestidores(String caminho) {
        List<Investidor> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // Pula o cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                String nome = d[0];
                String doc = d[1];
                double patri = Double.parseDouble(d[2]);
                String tipo = d[3]; // PF ou PJ
                String extra = d[4]; // Perfil ou Razão Social

                if (tipo.equalsIgnoreCase("PF")) {
                    lista.add(new model.Investidores.PessoaFisica(nome, doc, patri, extra));
                } else {
                    lista.add(new model.Investidores.Institucional(nome, doc, patri, extra));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler investidores: " + e.getMessage());
        }
        return lista;

        
    }
}