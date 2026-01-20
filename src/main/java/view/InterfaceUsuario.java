package view;

import java.util.List;
import java.util.Scanner;

// IMPORTANTE: Removido o "main.java" dos nomes dos pacotes
import model.Institucional;
import model.Investidor;
import model.PessoaFisica;

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

    public static void exibirMenuInvestidores() {
        System.out.println("\n--- MENU INVESTIDORES ---");
        System.out.println("1 - Cadastrar investidor");
        System.out.println("2 - Cadastrar investidor em lote");
        System.out.println("3 - Exibir todos investidores");
        System.out.println("4 - Excluir investidores (lista de CPFs/CNPJs)");
        System.out.println("5 - Selecionar Investidor por CPF ou CNPJ");
        System.out.println("0 - Voltar ao menu anterior");
        System.out.print("\nSelecione uma opção: ");
    }

    public static void exibirMensagemCarga(int quantidade) {
        System.out.println("\n[INFO]: " + quantidade + " registros processados com sucesso!");
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

    // Métodos de Leitura Limpos
    public static String lerNome() {
        System.out.print("Digite o nome: ");
        return scanner.nextLine();
    }

    public static String lerDocumento() {
        System.out.print("Digite o CPF/CNPJ: ");
        return scanner.nextLine();
    }

    public static double lerPatrimonio() {
        System.out.print("Digite o patrimônio inicial: ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static String lerPerfil() {
        System.out.print("Perfil (Conservador/Moderado/Arrojado): ");
        return scanner.nextLine();
    }

    public static String lerRazaoSocial() {
        System.out.print("Digite a Razão Social: ");
        return scanner.nextLine();
    }

    // MÉTODO CORRIGIDO: Recebe a lista e formata os dados
    public static void exibirListaInvestidores(List<Investidor> investidores) {
        System.out.println("\n--- LISTA DE INVESTIDORES ---");
        if (investidores.isEmpty()) {
            System.out.println("Nenhum investidor cadastrado.");
        } else {
            for (Investidor i : investidores) {
                String tipoInfo = "";
                if (i instanceof PessoaFisica) {
                    tipoInfo = " | Perfil: " + ((PessoaFisica) i).getPerfil();
                } else if (i instanceof Institucional) {
                    tipoInfo = " | Razão: " + ((Institucional) i).getRazaoSocial();
                }

                System.out.printf("Nome: %-20s | Doc: %-14s | Patrimônio: R$ %10.2f%s\n", 
                        i.getNome(), i.getDocumento(), i.getPatrimonioTotal(), tipoInfo);
            }
        }
        System.out.println("--------------------------------------------");
    }

    private static final String REGRA_DOC = " CPF precisa de 11 dígitos e CNPJ precisa de 14.";

    public static void exibirMensagemErroValidador(int codigoErro) {
        String inicioMensagem;
        
        switch (codigoErro) {
            case -1:
                System.err.println("\n[ERRO]: O documento não pode estar vazio.");
                return;
            case -2:
                inicioMensagem = "Documento muito curto.";
                break;
            case -3:
                inicioMensagem = "Tamanho de documento inválido.";
                break;
            case -4:
                inicioMensagem = "Documento muito longo.";
                break;
            case -5:
                inicioMensagem = "O documento contém caracteres inválidos (letras ou símbolos não permitidos).";
                break;
                case -6:
            inicioMensagem = "Este documento já está cadastrado no sistema.";
            System.err.println("\n[ERRO]: " + inicioMensagem);
            return; // Encerra aqui pois não precisa da regra de dígitos
            case -7:
                inicioMensagem = "Documento inválido (sequência de números repetidos).";
                break;
            default:
                inicioMensagem = "Erro desconhecido.";
                break;
        }
        
        System.err.println("\n[ERRO]: " + inicioMensagem + REGRA_DOC);
    }

}