package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;   

public class CarregarCSV {
    public static void main(String[] args) {

        String base = "C:/Users/Usuario/Desktop/projetosProg/Sistema-de-Gestao-de-Carteiras/src/resources/Arquivoscsv/";

        String [][] dadosCSV = {
        {"Ações", base + "acao.csv", "1"},
        {"FIIs", base + "fii.csv", "2"},
        {"Criptoativos", base + "criptoativo.csv", "3"}, 
        {"Stocks", base + "stock.csv", "4"}, 
        {"Tesouro", base + "tesouro.csv", "5"}
        };

        try{
            for (String[] dado : dadosCSV){

                String nomeArquivo = dado[0];
                String caminho = dado[1];
                String numID = dado[2];

                System.out.println("XXXXXX Lendo & Exibindo arquivo " + numID + ": "+ nomeArquivo  + " XXXXXX\n");
                leEMostraCSV(caminho);
                System.out.println("\n####################################################\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void leEMostraCSV(String caminho) throws IOException{
        try(BufferedReader leitor = new BufferedReader(new FileReader(caminho))){
            String linha;
            while ((linha = leitor.readLine()) != null){
                String[] valores = linha.split(",");
                for(String valor : valores) {
                    System.out.println(valor + "\t");
                    System.out.println();
                }
            }
        }
    }
}