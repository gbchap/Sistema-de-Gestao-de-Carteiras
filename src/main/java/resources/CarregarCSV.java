package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Ativos.Acao;
import model.Ativos.Ativo;
import model.Ativos.Criptoativo;
import model.Ativos.FII;
import model.Ativos.Stock;
import model.Ativos.Tesouro;
import model.Investidores.Investidor;

public class CarregarCSV {

    private static final String BASE_PATH = "Arquivoscsv/";
    
    public static List<Ativo> carregarTodosAtivos() {
        List<Ativo> listaCompleta = new ArrayList<>();

        listaCompleta.addAll(lerAcoes(BASE_PATH + "acao.csv"));
        listaCompleta.addAll(lerFIIs(BASE_PATH + "fii.csv"));
        listaCompleta.addAll(lerCriptos(BASE_PATH + "criptoativo.csv"));
        listaCompleta.addAll(lerStocks(BASE_PATH + "stock.csv"));
        listaCompleta.addAll(lerTesouro(BASE_PATH + "tesouro.csv"));

        return listaCompleta;
    }

    public static List<Acao> lerAcoes(String caminho) {
        List<Acao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); 
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

    public static List<FII> lerFIIs(String caminho) {
        List<FII> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
               
                String[] d = linha.split(";");

                if (d.length < 6 || d[3].equals("-")) continue; 

                try {
                    // 1. d[3].replace(".", "") -> Remove o ponto de milhar (ex: 1.051.35 vira 1051.35)
                    // 2. .replace(",", ".") -> Caso exista vírgula, troca por ponto para o padrão Java
                    String precoTratado = d[3].replace(".", "").replace(",", ".");
                    double preco = Double.parseDouble(precoTratado);

                    // repetir o tratamento para dividendos e taxas por segurança
                    double dividendo = Double.parseDouble(d[4].replace(".", "").replace(",", "."));
                    double taxa = Double.parseDouble(d[5].replace(".", "").replace(",", "."));

                    lista.add(new FII(d[1], d[0], preco, false, d[2], dividendo, taxa));
                } catch (NumberFormatException e) {
                    System.err.println("Falha ao converter valores na linha: " + linha);
                } 
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler fii.csv: " + e.getMessage());
        }
        return lista;
    }

    public static List<Criptoativo> lerCriptos(String caminho) {
        List<Criptoativo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço(USD);Consenso;Max
                String ticker = d[0];
                String nome = d[1];
                double preco = Double.parseDouble(d[2]);
                
                String consenso = (d.length > 3) ? d[3] : "N/A";
                String qtdMax = (d.length > 4) ? d[4] : "Ilimitado";

                lista.add(new Criptoativo(nome, ticker, preco, false, consenso, qtdMax, 5.39));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler criptoativo.csv: " + e.getMessage());
        }
        return lista;
    }

    public static List<Stock> lerStocks(String caminho) {
        List<Stock> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço(USD);Bolsa;Setor
                lista.add(new Stock(d[1], d[0], Double.parseDouble(d[2]), false, d[3], d[4], 5.39));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler stock.csv: " + e.getMessage());
        }
        return lista;
    }

    public static List<Tesouro> lerTesouro(String caminho) {
        List<Tesouro> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                // Ticker;Nome;Preço;Rendimento;Vencimento
                lista.add(new Tesouro(d[1], d[0], Double.parseDouble(d[2]), false, d[3], d[4]));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler tesouro.csv: " + e.getMessage());
        }
        return lista;
    }

    public static List<Investidor> lerInvestidores(String caminho) {
        List<Investidor> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); 
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                
                if (d.length < 8) {
                    System.err.println("[ERRO LOTE]: Linha ignorada por falta de dados: " + linha);
                    continue;
                }

                String nome = d[0];
                String doc = d[1];
                String tel = d[2];
                String nasc = d[3];
                String endereco = d[4];
                double patri = Double.parseDouble(d[5]);
                String tipo = d[6]; // PF ou PJ
                String extra = d[7]; // Perfil ou Razão Social
                
                if (tipo.equalsIgnoreCase("PF")) {
                    lista.add(new model.Investidores.PessoaFisica(nome, doc, tel, nasc, endereco, patri, extra));
                } else {
                    lista.add(new model.Investidores.Institucional(nome, doc, tel, nasc, endereco, patri, extra));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler investidores: " + e.getMessage());
        }
        return lista;
    }
}