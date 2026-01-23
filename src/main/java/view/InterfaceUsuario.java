package view;

import java.util.List;
import java.util.Scanner;

import model.Investidores.Institucional;
import model.Investidores.Investidor;
import model.Investidores.PessoaFisica;
import resources.Validador;

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
        System.out.println("1 - Cadastrar investidor"); //exibirmenuinvestidorselecionado()
        System.out.println("2 - Cadastrar investidor em lote");
        System.out.println("3 - Exibir todos investidores");
        System.out.println("4 - Excluir investidores (lista de CPFs/CNPJs)");
        System.out.println("5 - Selecionar Investidor por CPF ou CNPJ");
        System.out.println("0 - Voltar ao menu anterior");
        System.out.print("\nSelecione uma opção: ");
    }

    public static void exibirMenuInvestidorSelecionado(){
        System.out.println("\n--- MENU DO INVESTIDOR SELECIONADO ---");
        System.out.println("1 - Editar informações do investidor"); //patrimonio n pode ser negativo
        System.out.println("2 - Excluir investidor");
        System.out.println("3 - Exibir ativos do investidor");
        System.out.println("4 - Exibir valor total gasto");
        System.out.println("5 - Exibir valor total atual");
        System.out.println("6 - Exibir as porcentagens de produtos de renda fixa e de renda variável");
        System.out.println("7 - Exibir as porcentagens de produtos nacionais e de produtos internacionais");
        System.out.println("8 - Salvar relatório");
        System.out.println("9 - Adicionar uma movimentação de compra");
        System.out.println("10 - Adicionar uma movimentação de venda");
        System.out.println("11 - Adicionar lote de movimentações");
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
    System.err.println("\nERRO: Entrada inválida. Por favor, digite números correspondentes às opções.");
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
        while (true) {
            System.out.print("Digite o patrimônio inicial: ");
            String entrada = scanner.nextLine();
            try {
                double valor = Double.parseDouble(entrada.replace(",", "."));
                if (valor > 0) return valor;
                
                exibirMensagemErroValidador(-20); // Código que criamos: "Valor deve ser > 0"
            } catch (NumberFormatException e) {
                System.err.println("[ERRO]: Digite um valor numérico (ex: 1500.50)");
            }
        }
    }

    public static String lerPerfil() {
        while (true) {
            System.out.println("\n--- PERFIL DO INVESTIDOR ---");
            System.out.println("1 - Conservador");
            System.out.println("2 - Moderado");
            System.out.println("3 - Arrojado");
            System.out.print("Selecione (1-3): ");
            
            String op = scanner.nextLine();
            switch (op) {
                case "1": return "Conservador";
                case "2": return "Moderado";
                case "3": return "Arrojado";
                default:
                    exibirMensagemErroValidador(-30); // Código: "Opção inválida"
            }
        }
    }

    public static String lerRazaoSocial() {
        while (true) {
        System.out.print("Digite a Razão Social da Instituição: ");
        String razao = scanner.nextLine();
        int status = Validador.validarTexto(razao); // Reusando o erro -10 e -11
        
        if (status == 0) return razao;
        
        exibirMensagemErroValidador(status);
    }
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
        String mensagem = "";
        boolean incluirRegraDoc = false;

        switch (codigoErro) {
            // --- ERROS DE DOCUMENTO (Precisam da REGRA_DOC no final) ---
            case -1: mensagem = "O documento não pode estar vazio."; break;
            case -2: mensagem = "Documento muito curto."; incluirRegraDoc = true; break;
            case -3: mensagem = "Tamanho de documento inválido."; incluirRegraDoc = true; break;
            case -4: mensagem = "Documento muito longo."; incluirRegraDoc = true; break;
            case -5: mensagem = "O documento contém caracteres inválidos."; incluirRegraDoc = true; break;
            case -6: mensagem = "Este documento já está cadastrado no sistema."; break;
            case -7: mensagem = "Documento inválido (números repetidos)."; incluirRegraDoc = true; break;

            // --- ERROS GERAIS (NÃO precisam da REGRA_DOC) ---
            case -10: mensagem = "O campo não pode estar vazio."; break;
            case -11: mensagem = "O texto é muito curto (mínimo 3 caracteres)."; break;
            case -20: mensagem = "O valor deve ser maior que zero."; break;
            case -30: mensagem = "Opção inválida! Escolha uma opção do menu."; break;
            case -40: mensagem = "Data inválida. Use o formato dd/mm/aaaa."; break;
            case -41: mensagem = "A data de nascimento não pode ser no futuro."; break;
            
            default: mensagem = "Erro de entrada de dados."; break;
        }

        // Montagem 1da msg
        if (incluirRegraDoc) {
            System.err.println("\n[ERRO]: " + mensagem + REGRA_DOC);
        } else {
            System.err.println("\n[ERRO]: " + mensagem);
        }
    }

}