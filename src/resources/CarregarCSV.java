package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                String[] d = linha.split(";");
                // Ticker;Nome;Setor;Preço;Dividendo;Taxa
                if (d[3].equals("-")) continue; 
                lista.add(new FII(d[1], d[0], Double.parseDouble(d[3]), false, d[2], 
                          Double.parseDouble(d[4]), Double.parseDouble(d[5])));
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
                lista.add(new Criptoativo(d[1], d[0], Double.parseDouble(d[2]), false, d[3], d[4], 5.39));
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
}