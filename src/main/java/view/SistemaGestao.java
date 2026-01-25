package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Exceptions.OpcaoInvalidaException;
import resources.CarregarCSV;
import resources.Validador;
import view.Menus.MenuPrincipal;
import model.*;
import model.Ativos.Acao;
import model.Ativos.Ativo;
import model.Ativos.Criptoativo;
import model.Ativos.FII;
import model.Ativos.Stock;
import model.Ativos.Tesouro;
import model.Investidores.Institucional;
import model.Investidores.Investidor;
import model.Investidores.PessoaFisica;

public class SistemaGestao {
    private static List<Ativo> bancoDeAtivos = new ArrayList<>();
    private static List<Investidor> listaInvestidores = new ArrayList<>();
    public static void main(String[] args) {
        try {
            // Carga inicial obrigatória 
            bancoDeAtivos = CarregarCSV.carregarTodosAtivos();
            ControleUsuario.exibirMensagemCarga(bancoDeAtivos.size());
        } catch (Exception e) {
            ControleUsuario.exibirErroCustomizado(e.getMessage());
        }

        ControleUsuario.exibirBoasVindas();
        new MenuPrincipal().executar();
    }

    //###############################  MENU ATIVOS ################################### 

    public static void cadastrarAtivo() {
        // A View agora já entrega tudo validado
        String nome = ControleUsuario.lerNome();
        String ticker = ControleUsuario.lerTicker();
        double preco = ControleUsuario.lerPrecoAtual();
        boolean qualificado = ControleUsuario.lerQualificado();
        int tipo = ControleUsuario.lerTipoAtivo();

        Ativo novo = criarAtivoPorTipo(tipo, nome, ticker, preco, qualificado);
        
        bancoDeAtivos.add(novo);
        ControleUsuario.exibirMensagemCarga(1);
    }

    private static Ativo criarAtivoPorTipo(int tipo, String n, String t, double p, boolean q) {
        return switch (tipo) {
            case 1 -> new Acao(n, t, p, q);
            case 2 -> new FII(n, t, p, q, "Geral", 0.0, 0.0);
            case 3 -> new Criptoativo(n, t, p, q, "N/A", "Ilimitado", 5.39);
            case 4 -> new Stock(n, t, p, q, "NYSE", "Setor", 5.39);
            case 5 -> new Tesouro(n, t, p, q, "Prefixado", "01/01/2030");
            default -> throw new IllegalArgumentException("Tipo inválido");
        };
    }

    private static Ativo buscarAtivoPorTicker(String ticker) {
        for (Ativo a : bancoDeAtivos) {
            if (a.getTicker().equalsIgnoreCase(ticker)) return a;
            }
        return null;
    }

    public static void exibirAtivos(int num) {
        // num vem do MenuAtivos: (opcao - 6)
        // -1: Todos, 0: Ações, 1: FIIs, 2: Cripto, 3: Stocks, 4: Tesouro

        if (num == -1) { // Caso "Todos os ativos" (Opção 5 do menu)
            ControleUsuario.exibirTabelaAtivos(bancoDeAtivos);
            return;
        }

        List<Ativo> filtrados = new ArrayList<>();
        for (Ativo a : bancoDeAtivos) {
            // Filtra usando 'instanceof' para saber a subclasse real do Ativo
            if (num == 0 && a instanceof model.Ativos.Acao) filtrados.add(a);
            else if (num == 1 && a instanceof model.Ativos.FII) filtrados.add(a);
            else if (num == 2 && a instanceof model.Ativos.Criptoativo) filtrados.add(a);
            else if (num == 3 && a instanceof model.Ativos.Stock) filtrados.add(a);
            else if (num == 4 && a instanceof model.Ativos.Tesouro) filtrados.add(a);
        }

        if (filtrados.isEmpty()) {
            ControleUsuario.exibirErroCustomizado("Nenhum ativo encontrado para esta categoria.");
        } else {
            ControleUsuario.exibirTabelaAtivos(filtrados);
        }
    }

    public static void cadastrarAtivoLote(){
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Digite o caminho do arquivo (ex: Arquivoscsv/ativos.csv): ");
        String caminho = sc.nextLine();

        int tipo = ControleUsuario.lerTipoAtivo();

        List<? extends Ativo> novos; 
        
        switch(tipo){
            case 1 -> novos = CarregarCSV.lerAcoes(caminho);
            case 2 -> novos = CarregarCSV.lerFIIs(caminho);
            case 3 -> novos = CarregarCSV.lerCriptos(caminho);
            case 4 -> novos = CarregarCSV.lerStocks(caminho);
            case 5 -> novos = CarregarCSV.lerTesouro(caminho);
            default->{
                ControleUsuario.exibirErroCustomizado("Tipo inválido para carga em lote.");
                return;
            }
        }
        int adicionados = 0;
        if (novos != null) {
            for (Ativo novoAtivo : novos) {
                if (buscarAtivoPorTicker(novoAtivo.getTicker()) == null) {
                bancoDeAtivos.add(novoAtivo);
                adicionados++;
                }
            }
        }
        ControleUsuario.exibirMensagemCarga(adicionados);
    }

    public static void editaAtivo(){
        System.out.println("Exibindo os ativos cadastrados..."); //do tipo x especifico do ativo que vc quer editar
        int num = ControleUsuario.lerTipoAtivo() - 1;
        exibirAtivos(num);

        System.out.println("\nDigite o número correspondente ao ativo que deseja editar: ");
        int index = ControleUsuario.lerOpcao() - 1;
        
        if (index < 0 || index >= bancoDeAtivos.size()) {
            ControleUsuario.exibirErroCustomizado("Índice inválido.");
            return;
        }

        Ativo ativo = bancoDeAtivos.get(index);

        System.out.println("Digite a propriedade do ativo que deseja editar: ");
        System.out.println("1. Nome");
        System.out.println("2. Ticker");
        System.out.println("3. Preço Atual");
        System.out.println("4. Qualificado");

        int propriedade = ControleUsuario.lerOpcao();
        switch(propriedade){
            case 1:
                String novoNome = ControleUsuario.lerNome();
                ativo.setNome(novoNome);
            case 2:
                String novoTicker = ControleUsuario.lerTicker();
                ativo.setTicker(novoTicker); //nenhum desses tem verificação pois elas ja tao nos setters
            case 3:
                double novoPreco = ControleUsuario.lerPrecoAtual();
                ativo.setPrecoAtual(novoPreco);
            case 4:
                boolean novoQualificado = ControleUsuario.lerQualificado();
                ativo.setQualificado(novoQualificado);
        }
    }


    public static void excluiAtivo() {
        String ticker = ControleUsuario.lerTicker();
        Ativo alvo = null;
        
        for (Ativo a : bancoDeAtivos) {
            if (a.getTicker().equalsIgnoreCase(ticker)) {
                alvo = a;
                break;
            }
        }
        if (alvo != null) {
            bancoDeAtivos.remove(alvo);
            for (Investidor inv : listaInvestidores) {
                inv.getCarteira().removerAtivoPorTicker(ticker);
            }
            
            ControleUsuario.exibirSucesso("Ativo removido do sistema e de todas as carteiras com sucesso.");
        } else {
            ControleUsuario.exibirErroCustomizado("Ativo com ticker " + ticker + " não encontrado.");
        }
    }
    

    //###############################  MENU INVESTIDOR ################################### 
    public static void cadastrarInvestidor() {
        String nome;
        while (true) {
            nome = ControleUsuario.lerNome();
            int statusNome = Validador.validarTexto(nome);
            if (statusNome == 0) break;
            ControleUsuario.exibirMensagemErroValidador(statusNome); // Exibe erro -11
        }
        //validação da entrada do documento:
        String docRaw; // Declarada aqui para ser usada no loop
        int statusDoc;
        while (true) {
            docRaw = ControleUsuario.lerDocumento();
            statusDoc = Validador.validarDocumento(docRaw);

            if (statusDoc == 0) {
            // Se o formato estiver ok, agora conferimos a duplicidade (To-Do -6)
            String docLimpoTemp = Validador.limparDocumento(docRaw);
            if (buscarInvestidor(docLimpoTemp) != null) {
                ControleUsuario.exibirMensagemErroValidador(-6);
                continue;
            }
            break; 
            } else {
                ControleUsuario.exibirMensagemErroValidador(statusDoc);
            }
        }

        String docLimpo = Validador.limparDocumento(docRaw);
        String telefone = ControleUsuario.lerTelefone();
        String dataNasc = ControleUsuario.lerDataNascimento();
        String endereco = ControleUsuario.lerEndereco();
        double patrimonio = ControleUsuario.lerPrecoAtual();
        
        if (Validador.isCPF(docLimpo)) { 
            String perfil = ControleUsuario.lerPerfil();
            listaInvestidores.add(new PessoaFisica(nome, docLimpo, telefone, dataNasc, endereco, patrimonio, perfil));
        } else { 
            String razao = ControleUsuario.lerRazaoSocial();
            listaInvestidores.add(new Institucional(nome, docLimpo, telefone, dataNasc, endereco, patrimonio, razao));
        }
        
        ControleUsuario.exibirMensagemCarga(1); 
    }

    public static Investidor buscarInvestidor(String doc) {
        for (Investidor inv : listaInvestidores) {
            if (inv.getDocumento().equals(doc)) {
                return inv;
            }
        }
        return null; 
    }


    public static void listarInvestidores() {
        if (listaInvestidores.isEmpty()) {
            ControleUsuario.exibirErroCustomizado("Nenhum investidor no sistema.");
            return;
        }
        ControleUsuario.exibirListaInvestidores(listaInvestidores);
    }


    private static Investidor investidorLogado = null;

    public static void selecionarInvestidor() {
        String doc = ControleUsuario.lerDocumentoValidado();
        Investidor encontrado = buscarInvestidor(doc); 

        if (encontrado != null) {
            investidorLogado = encontrado;
            ControleUsuario.exibirMensagemCarga(1); 
            
            new view.Menus.MenuInvestidorSelected().executar(); 
            
            investidorLogado = null; 
        } else {
            ControleUsuario.exibirErroCustomizado("Investidor não encontrado.");
        }
    }

    
    //Regra de Negócio: Verifica se o investidor logado tem permissão para o ativo.
    
    public static boolean validarPermissaoInvestimento(Ativo ativo) {
        if (investidorLogado instanceof Institucional) return true; // Institucional pode tudo [cite: 207]
        
        PessoaFisica pf = (PessoaFisica) investidorLogado;
        String perfil = pf.getPerfil();

        // 1. Trava de Qualificado (Patrimônio >= 1M)
        if (ativo.isQualificado() && !pf.isQualificado()) {
            ControleUsuario.exibirErroCustomizado("Ativo restrito a investidores qualificados.");
            return false;
        }

        // 2. Trava de Criptoativos (Apenas Arrojados)
        if (ativo instanceof Criptoativo && !perfil.equals("Arrojado")) {
            ControleUsuario.exibirErroCustomizado("Apenas perfis Arrojados podem operar Criptoativos.");
            return false;
        }

        // 3. Trava de Stocks (Moderado ou Arrojado)
        if (ativo instanceof Stock && perfil.equals("Conservador")) {
            ControleUsuario.exibirErroCustomizado("Perfis Conservadores não podem operar Stocks.");
            return false;
        }

        return true;
    }

    //###############################  MENU INVESTIDOR SELECIONADO ################################### 

    public static void salvarRelatorio() {
        // 1. Verifica se tem alguém selecionado
        if (investidorLogado == null) {
            ControleUsuario.exibirErroCustomizado("Selecione um investidor primeiro!");
            return;
        }

        try {
            // 2. AQUI chamamos o ExportadorRelatorio que você criou!
            // Ele vai fazer todo o trabalho de criar o StringBuilder e o Arquivo
            resources.ExportadorRelatorio.gerarArquivoJson(investidorLogado);

            // 3. Pegamos o nome do arquivo para avisar o usuário
            String nomeArquivo = "relatorio_" + investidorLogado.getDocumento() + ".json";
            
            // 4. Avisamos na tela (isso é o que faltava para você ver algo acontecer)
            ControleUsuario.exibirSucessoExportacao(nomeArquivo);

        } catch (java.io.IOException e) {
            ControleUsuario.exibirErroCustomizado("Erro ao gravar no disco: " + e.getMessage());
        }
        
    }

    //se der passar para a parte de textos para a view
    public static void editarInfoInvestidor() {
        // Note que não usamos System.out aqui, delegamos para a View
        investidorLogado.setNome(ControleUsuario.lerNome());
        
        // Usando os novos métodos que criamos na ControleUsuario
        String novoTel = ControleUsuario.lerTelefone();
        String novoEnd = ControleUsuario.lerEndereco();
        double novoPatri = ControleUsuario.lerPrecoAtual();
        
        // Agora os métodos set não darão mais erro
        investidorLogado.setTelefone(novoTel);
        investidorLogado.setEndereco(novoEnd);
        investidorLogado.setPatrimonioTotal(novoPatri);
        
        ControleUsuario.exibirSucesso("Dados atualizados com sucesso!");
    }

    public static void excluirInvestidor() {
        listaInvestidores.remove(investidorLogado);
        investidorLogado = null; 
        ControleUsuario.exibirSucesso("Investidor e sua carteira foram removidos.");
    }

    // Item 3 - Exibir ativos do investidor [cite: 129]
    public static void exibirAtivosInvestidor() {
        List<ItemCarteira> itens = investidorLogado.getCarteira().getItens();
        if (itens.isEmpty()) {
            ControleUsuario.exibirErroCustomizado("Investidor não possui ativos na carteira.");
        } else {
            ControleUsuario.exibirTabelaItensCarteira(investidorLogado.getNome(), itens);
        }
    }

    public static void exibirValorTotalGasto() {
        double total = 0;
        for (var item : investidorLogado.getCarteira().getItens()) {
            total += item.getQuantidade() * item.getPrecoMedio();
        }
        System.out.printf("\n[RELATÓRIO]: Valor Total Gasto: R$ %.2f\n", total);
    }

    public static void exibirValorTotalAtual() {
        double total = investidorLogado.getCarteira().getValorTotalEmReais();
        System.out.printf("\n[RELATÓRIO]: Valor Total Atual (Patrimônio em Ativos): R$ %.2f\n", total);
    }   

    public static void porcentRendas() {
        double rf = investidorLogado.getCarteira().getPercentualRendaFixa();
        double rv = investidorLogado.getCarteira().getPercentualRendaVariavel();
        System.out.printf("\nAlocação: Renda Fixa: %.2f%% | Renda Variável: %.2f%%\n", rf, rv);
    }

    public static void porcentProdutos() {
        double nac = investidorLogado.getCarteira().getPercentualNacional();
        double inter = investidorLogado.getCarteira().getPercentualInternacional();
        System.out.printf("\nGeografia: Nacional: %.2f%% | Internacional: %.2f%%\n", nac, inter);
    }

        public static void adicionarMovVenda() {
            String ticker = ControleUsuario.lerTicker();
            double qtd = ControleUsuario.lerQuantidade();

            boolean sucesso = investidorLogado.venderAtivo(ticker, qtd);
            
            if (sucesso) {
                ControleUsuario.exibirSucesso("Venda realizada!");
            } else {
                ControleUsuario.exibirErroCustomizado("Quantidade insuficiente ou ativo não possuído.");
            }
        }
        

    public static void adicionarLoteMov() {
        // 1. Interação com o usuário delegada para a View
        String caminho = ControleUsuario.lerCaminhoArquivo();

        // 2. Uso de Try-with-resources para garantir o fechamento do arquivo
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(caminho))) {
            String linha = br.readLine(); // Pular cabeçalho
            int processados = 0;
            
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";"); // Formato esperado: Ticker;Tipo;Quantidade;Preco
                
                // Validação simples de colunas para evitar IndexOutOfBounds
                if (d.length < 4) continue;

                Ativo a = buscarAtivoPorTicker(d[0]);
                
                // Verifica se o ativo existe e se o investidor tem permissão (Perfil)
                if (a != null && validarPermissaoInvestimento(a)) {
                    try {
                        double qtd = Double.parseDouble(d[2].replace(",", "."));
                        double preco = Double.parseDouble(d[3].replace(",", "."));
                        
                        if (d[1].equalsIgnoreCase("Compra")) {
                            investidorLogado.comprarAtivo(a, qtd, preco);
                            processados++;
                        } else if (d[1].equalsIgnoreCase("Venda")) {
                            boolean sucesso = investidorLogado.venderAtivo(d[0], qtd);
                            if (sucesso) processados++;
                        }
                    } catch (NumberFormatException e) {
                        // Pula linhas com valores numéricos inválidos sem travar o sistema
                    }
                }
            }
            // 3. Feedback de sucesso via View
            ControleUsuario.exibirMensagemCarga(processados);

        } catch (java.io.FileNotFoundException e) {
            ControleUsuario.exibirErroCustomizado("Arquivo não encontrado no caminho: " + caminho);
        } catch (java.io.IOException e) {
            ControleUsuario.exibirErroCustomizado("Erro ao ler o arquivo: " + e.getMessage());
        }
    }



    public static void adicionarMovCompra() {
        String ticker = ControleUsuario.lerTicker();
        Ativo ativo = buscarAtivoPorTicker(ticker);

        if (ativo == null) {
            ControleUsuario.exibirErroCustomizado("Ativo não encontrado no sistema.");
            return;
        }

        if (!validarPermissaoInvestimento(ativo)) return;

        double qtd = ControleUsuario.lerQuantidade();
        double preco = ativo.getPrecoAtual(); 

        investidorLogado.comprarAtivo(ativo, qtd, preco);
        ControleUsuario.exibirSucesso("Compra realizada com sucesso!");
    }

    public static void excluirInvestidores() {
        String listaDocs = ControleUsuario.lerListaDocumentos(); // Crie este método na View para ler a String
        String[] docs = listaDocs.split(",");
        int removidos = 0;

        for (String d : docs) {
            String limpo = Validador.limparDocumento(d.trim());
            Investidor inv = buscarInvestidor(limpo);
            if (inv != null) {
                listaInvestidores.remove(inv);
                removidos++;
            }
        }
        ControleUsuario.exibirMensagemCarga(removidos);
    }

  
    public static void cadastrarInvestidorLote() {
    System.out.print("Digite o caminho do arquivo (ex: Arquivoscsv/investidores.csv): ");
    
    java.util.Scanner scannerManual = new java.util.Scanner(System.in);
    String caminho = scannerManual.nextLine();

    List<Investidor> novos = resources.CarregarCSV.lerInvestidores(caminho);
    int adicionados = 0;

    for (Investidor novo : novos) {
        if (buscarInvestidor(novo.getDocumento()) == null) {
            listaInvestidores.add(novo);
            adicionados++;
        }
    }
    view.ControleUsuario.exibirMensagemCarga(adicionados);
}
}