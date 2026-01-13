package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.Exceptions.OpcaoInvalidaException; 

public class SistemaGestao{

    public static void exibirMenuPrincipal(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n#######################################################\n");
        System.out.println("Bem-vindo(a) ao Sistema de Gestão de Carteiras Financeiras");
        System.out.println("\n#######################################################\n");

        System.out.println("MENU PRINCIPAL");
        System.out.println("-------------------------------------------------------");
        System.out.println("\n1 - Ativos");
        System.out.println("2 - Investidores");
        System.out.println("0 - Sair");

        try{
            System.out.println("\nSelecione uma opção: ");
            int opcao = scanner.nextInt();
                
            if (opcao == 0){
                System.out.println("Encerrando o sistema...");
            }else{
                processaOpcao(opcao);
            }
                
        }catch (InputMismatchException e){
                System.err.println("ERRO: Entrada inválida. Por favor, digite apenas números inteiros.");
                scanner.nextLine();
        }catch (OpcaoInvalidaException e) {
                System.err.println("ERRO: " + e.getMessage());
        }finally{
                System.out.println("-------------------------------------------------------");
        }

        scanner.close();    
    }


    public static void processaOpcao(int opcao) throws OpcaoInvalidaException {

        System.out.println("\n#######################################################\n");
        System.out.println("Carregando opção " + opcao + "...");
        System.out.println("\n#######################################################\n");

        switch (opcao){

            case 1:
                System.out.println("MENU ATIVOS:");
                System.out.println("-------------------------------------------------------");
                System.out.println("\n1 - Cadastrar ativo; ");
                System.out.println("2 - Adicionar outros ativos; ");
                System.out.println("3 - Cadastrar ativo em lote; ");
                System.out.println("4 - Editar ativo;");
                System.out.println("5 - Excluir ativo;");
                System.out.println("6 - Cadastrar ativo em lote; ");
                System.out.println("Exibir relatório de ativos: ");
                System.out.println("    7 - Todos os ativos;");
                System.out.println("    8 - Apenas Ações;");
                System.out.println("    9 - Apenas FIIs;");
                System.out.println("    10 - Apenas Criptoativos;");
                System.out.println("    11 - Apenas Stocks;");
                System.out.println("    12 - Apenas Tesouro;");
                System.out.println("\n0 - Voltar ao Menu Principal;");
                System.out.println("\n#######################################################");

                ProcessaAtivos();

            case 2:
                System.out.println("MENU INVESTIDORES:");
                System.out.println("-------------------------------------------------------");
                System.out.println("\n1 - Cadastrar investidor; ");
                System.out.println("2 - Cadastrar investidor em lote; ");
                System.out.println("3 - Exibir todos investidores; ");
                System.out.println("4 - Excluir investidores por CPFs/CNPJs;");
                System.out.println("5 - Selecionar Investidor por CPF ou CNPJ;");
                System.out.println("\n0 - Voltar ao Menu Principal;");
                System.out.println("\n#######################################################");
                ProcessaInvestidores();
            default:
                throw new OpcaoInvalidaException(opcao);
                
        }
    }

    public static void ProcessaAtivos(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelecione uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao){
            case 1:
                //carrega coisa
                break;
            case 2:
                //carrega coisa
                break;
            case 3:
                //carrega coisa
                break;
            case 4:
                //carrega coisa
                break;
            case 5:
                //carrega coisa
                break;
            case 6:
                //carrega coisa
                break;
            case 7:
                //carrega coisa
                break;
            case 8:
                //carrega coisa
                break;
            case 9:
                //carrega coisa
                break;
            case 10:
                //carrega coisa
                break;
            case 11:
                //carrega coisa
                break;
            case 12:
                //carrega coisa
                break;
            case 0:
                exibirMenuPrincipal();
        }
        scanner.close();
    }


    public static void ProcessaInvestidores(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelecione uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao){
         case 1:
                //carrega coisa
                break;
            case 2:
                //carrega coisa
                break;
            case 3:
                //carrega coisa
                break;
            case 4:
                //carrega coisa
                break;
            case 5:
                //Ao selecionar um investidor o sistema deve oferecer as
                //seguintes opções: etc etc e dps uma opção para voltar pra esse menu
                break;
            case 0:
                exibirMenuPrincipal();
        }
        scanner.close();
    }

    public static void main(String[] args){
        exibirMenuPrincipal();
    }
}
