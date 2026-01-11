package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.Exceptions.OpcaoInvalidaException; 

public class SistemaGestao{
     public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar){
            exibirMenu();

            try{
                System.out.println("\nSelecione uma opção: ");
                int opcao = scanner.nextInt();
                
                if (opcao == 0){
                    System.out.println("Encerrando o sistema...");
                    continuar = false;
                }else{
                    processaOpcao(opcao);
                }
                
            }catch (InputMismatchException e){
                System.err.println("ERRO: Entrada inválida. Por favor, digite apenas números inteiros.");
                scanner.nextLine();
            }catch (OpcaoInvalidaException e) {
                System.err.println("ERRO DE NEGÓCIO: " + e.getMessage());
            }finally{
                System.out.println("-------------------------------------------------------");
            }
        }

        scanner.close();
    }

    public static void exibirMenu(){
        System.out.println("\n#######################################################\n");
        System.out.println("Bem-vindo(a) ao Sistema de Gestão de Carteiras Financeiras");
        System.out.println("\n#######################################################\n");

        System.out.println("MENU PRINCIPAL");
        System.out.println("\n1 - Ativos");
        System.out.println("2 - Investidores");
        System.out.println("0 - Sair");
    }

    public static void processaOpcao(int opcao) throws OpcaoInvalidaException {

        System.out.println("\n#######################################################\n");
        System.out.println("Carregando opção " + opcao + "...");
        System.out.println("\n#######################################################\n");

        switch (opcao){
            case 1:
                //carrega ativos
                break;
            case 2:
                //carrega investidores
                break;
            default:
                throw new OpcaoInvalidaException(opcao);
        }
    }
}
