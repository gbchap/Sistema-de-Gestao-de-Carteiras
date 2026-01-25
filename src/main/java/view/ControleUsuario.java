package view;

import java.util.List;
import java.util.Scanner;

import model.Ativos.*;
import model.Investidores.Institucional;
import model.Investidores.Investidor;
import model.Investidores.PessoaFisica;
import resources.Validador;


public class ControleUsuario {
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

    public static void exibirMenuInvestidorSelecionado(){
        System.out.println("\n--- MENU DO INVESTIDOR SELECIONADO ---");
        System.out.println("1 - Editar informações do investidor");
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

    private static String lerTextoValidado(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            int status = Validador.validarTexto(entrada);
            if (status == 0) return entrada;
            exibirMensagemErroValidador(status);
        }
    }

    public static String lerNome() { return lerTextoValidado("Digite o nome: "); }
    public static String lerTicker() { return lerTextoValidado("Digite o ticker: "); }

    public static int lerTipoAtivo() {
        while (true) {
            System.out.println("\n--------- SELECIONE O TIPO DE ATIVO ---------");
            System.out.println("1-Ação | 2-FII | 3-Cripto | 4-Stock | 5-Tesouro");
            System.out.print("Opção: ");
            int op = lerOpcao();
            if (op >= 1 && op <= 5) return op;
            exibirMensagemErroValidador(-30); 
        }
    }

    public static double lerPrecoAtual(){
        while (true) {
            System.out.print("Digite o preço/patrimônio atual: ");
            String entrada = scanner.nextLine();
            try {
                double valor = Double.parseDouble(entrada.replace(",", "."));
                if (valor >= 0) return valor;
                
                exibirMensagemErroValidador(-20);
            } catch (NumberFormatException e) {
                System.err.println("[ERRO]: Digite um valor numérico (ex: 1500.50)");
            }
        }
    }

    public static boolean lerQualificado() {
        while (true) {
            System.out.print("O ativo é restrito a investidores qualificados? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) return true;
            if (resposta.equals("n")) return false;
            System.err.println("[ERRO]: Responda com 's' para sim ou 'n' para não.");
        }
    }

    public static String lerDocumento() {
        System.out.print("Digite o CPF/CNPJ: ");
        return scanner.nextLine();
    }

    public static String lerPerfil() {
        while (true) {
            System.out.println("\n--------- PERFIL DO INVESTIDOR ---------");
            System.out.println("1 - Conservador");
            System.out.println("2 - Moderado");
            System.out.println("3 - Arrojado");
            System.out.println("Selecione (1-3): ");
            
            String op = scanner.nextLine();
            switch (op) {
                case "1": return "Conservador";
                case "2": return "Moderado";
                case "3": return "Arrojado";
                default:
                    exibirMensagemErroValidador(-30); 
            }
        }
    }

    public static String lerRazaoSocial() {
        while (true) {
            System.out.print("Digite a Razão Social da Instituição: ");
            String razao = scanner.nextLine();
            int status = Validador.validarTexto(razao);
        
            if (status == 0) return razao;
        
            exibirMensagemErroValidador(status);
        }
    }

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
        System.out.println("-------------------------------------------------");
    }

    private static final String REGRA_DOC = " CPF precisa de 11 dígitos e CNPJ precisa de 14.";

    public static void exibirMensagemErroValidador(int codigoErro) {
        String mensagem = "";
        boolean incluirRegraDoc = false;

        switch (codigoErro) {
            
            case -1: mensagem = "O documento não pode estar vazio."; break;
            case -2: mensagem = "Documento muito curto."; incluirRegraDoc = true; break;
            case -3: mensagem = "Tamanho de documento inválido."; incluirRegraDoc = true; break;
            case -4: mensagem = "Documento muito longo."; incluirRegraDoc = true; break;
            case -5: mensagem = "O documento contém caracteres inválidos."; incluirRegraDoc = true; break;
            case -6: mensagem = "Este documento já está cadastrado no sistema."; break;
            case -7: mensagem = "Documento inválido (números repetidos)."; incluirRegraDoc = true; break;

            
            case -10: mensagem = "O campo não pode estar vazio."; break;
            case -11: mensagem = "O texto é muito curto (mínimo 3 caracteres)."; break;
            case -20: mensagem = "O valor deve ser maior que zero."; break;
            case -30: mensagem = "Opção inválida! Escolha uma opção do menu."; break;
            case -40: mensagem = "Data inválida. Use o formato dd/mm/aaaa."; break;
            case -41: mensagem = "A data de nascimento não pode ser no futuro."; break;
            case -50: mensagem = "Telefone inválido! Digite apenas números com DDD (ex: 32988887777)."; break;
            
            default: mensagem = "Erro de entrada de dados."; break;
        }

        if (incluirRegraDoc) {
            System.err.println("\n[ERRO]: " + mensagem + REGRA_DOC);
        } else {
            System.err.println("\n[ERRO]: " + mensagem);
        }
    }

    public static void exibirTabelaAtivos(List<model.Ativos.Ativo> ativos) {
        System.out.println("\n--------------- RELATÓRIO DE ATIVOS ------------------ ");
        System.out.printf("%-10s | %-30s | %-12s | %-10s\n", "Ticker", "Nome", "Preço", "Qualificado");
        System.out.println("-".repeat(70));
        for (model.Ativos.Ativo a : ativos) {
            System.out.printf("%-10s | %-30s | R$ %-10.2f | %-10s\n",
                a.getTicker(), a.getNome(), a.getPrecoAtual(), (a.isQualificado() ? "Sim" : "Não"));
        }
    }

    public static String lerDocumentoValidado() {
        while (true) {
            System.out.print("Digite o CPF/CNPJ do investidor: ");
            String docRaw = scanner.nextLine();
            int status = Validador.validarDocumento(docRaw); //
            if (status == 0) return Validador.limparDocumento(docRaw);
            exibirMensagemErroValidador(status);
        }
    }

    public static void exibirSucessoExportacao(String nomeArquivo) {
        System.out.println("\n[SISTEMA]: Relatório gerado com sucesso!");
        System.out.println("[ARQUIVO]: " + nomeArquivo);
    }

    public static double lerQuantidade() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Digite a quantidade (pode usar casas decimais para criptos): ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Erro! Digite um valor numérico válido: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    public static void exibirSucesso(String mensagem) {
        System.out.println("\n[SUCESSO] " + mensagem);
    }

    public static void exibirTabelaItensCarteira(String nomeInvestidor, List<model.ItemCarteira> itens) {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("            CARTEIRA DE ATIVOS - " + nomeInvestidor.toUpperCase());
        System.out.println("=".repeat(65));
        System.out.printf("%-10s | %-8s | %-18s | %-18s\n", "TICKER", "QTD", "VALOR GASTO(R$)", "VALOR ATUAL(R$)");
        System.out.println("-".repeat(65));

        for (var item : itens) {
            double valorGasto = item.getQuantidade() * item.getPrecoMedio();
            double valorAtual = item.getQuantidade() * item.getAtivo().getPrecoEmReais();

            System.out.printf("%-10s | %-8.2f | %-18.2f | %-18.2f\n",
                item.getAtivo().getTicker(),
                item.getQuantidade(),
                valorGasto,
                valorAtual);
        }

        System.out.println("=".repeat(65));
    }

    public static String lerTelefone() {
        System.out.print("Digite o novo telefone (apenas números): ");
        return scanner.nextLine();
    }

    public static String lerDataNascimento() {
        while (true) {
            System.out.print("Digite a data de nascimento (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            int status = Validador.validarData(data); 
            if (status == 0) return data;
            exibirMensagemErroValidador(status);
        }
    }

    public static String lerEndereco() {
        return lerTextoValidado("Digite o endereço completo (Rua, Número, Bairro, CEP, Cidade, Estado): ");
    }

    public static String lerListaDocumentos() {
        System.out.println("\nDigite os CPFs ou CNPJs separados por vírgula para exclusão:");
        System.out.print("Exemplo (12345678901, 98765432100): ");
        return scanner.nextLine();
    }

    public static String lerCaminhoArquivo() {
        System.out.print("Digite o caminho do arquivo de movimentações (ex: Arquivoscsv/movs.csv): ");
        return scanner.nextLine();
    }

    public static String lerInstituicao() {
        return lerTextoValidado("Digite o nome da instituição (ex: NuInvest, XP, Binance): ");
    }
}
