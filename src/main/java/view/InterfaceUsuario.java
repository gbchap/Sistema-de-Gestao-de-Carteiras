package view;

import java.util.Scanner;

/**
 * Fiz essa classe separada para deixar todas as mensagens em um só lugar e deixar a gestão mais "limpa"
 */
public class InterfaceUsuario {
    private static final Scanner scanner = new Scanner(System.in);

    public static void exibirBoasVindas() {
        System.out.println("\n#######################################################");
        System.out.println("Bem-vindo(a) ao Sistema de Gestão de Carteiras Financeiras");
        System.out.println("#######################################################\n");
    }

    public static void exibirMenuPrincipal() {
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1 - Ativos");
        System.out.println("2 - Investidores");
        System.out.println("0 - Sair");
        System.out.print("\nSelecione uma opção: ");
    }

    public static void exibirMenuAtivos() {
        System.out.println("\n--- MENU ATIVOS ---");
        System.out.println("1 - Cadastrar ativo");
        System.out.println("2 - Cadastrar ativo em lote");
        System.out.println("3 - Editar ativo");
        System.out.println("4 - Excluir ativo");
        System.out.println("\nExibir relatório de ativos:");
        System.out.println("5 - Todos os ativos");
        System.out.println("6 - Apenas Ações");
        System.out.println("7 - Apenas FIIs");
        System.out.println("8 - Apenas Criptoativos");
        System.out.println("9 - Apenas Stocks");
        System.out.println("10 - Apenas Tesouro");
        System.out.println("\n0 - Voltar ao Menu Principal");
        System.out.print("\nSelecione uma opção: ");
    }

    public static void exibirMensagemCarga(int quantidade) {
        System.out.println("\n[INFO]: " + quantidade + " ativos carregados com sucesso!");
    }

    public static void exibirEncerrando() {
        System.out.println("\nEncerrando o sistema...");
    }

    public static void exibirErroEntrada() {
        System.err.println("\nERRO: Entrada inválida. Por favor, digite apenas números.");
    }

    public static void exibirErroCustomizado(String erro) {
        System.err.println("\nERRO: " + erro);
    }

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    
}