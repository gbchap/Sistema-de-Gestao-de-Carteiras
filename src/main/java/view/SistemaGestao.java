package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Exceptions.OpcaoInvalidaException;
import resources.CarregarCSV;
import resources.Validador;
import view.Menus.MenuPrincipal;
import model.*;
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

    public static void cadastrarAtivoLote(){

    }

    public static void editaAtivo(){

    }

    public static void excluiAtivo(){

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
        double patrimonio = ControleUsuario.lerPrecoAtual();
        
        if (Validador.isCPF(docLimpo)) { 
            String perfil = ControleUsuario.lerPerfil();
            listaInvestidores.add(new PessoaFisica(nome, docLimpo, patrimonio, perfil));
        } else { 
            String razao = ControleUsuario.lerRazaoSocial();
            listaInvestidores.add(new Institucional(nome, docLimpo, patrimonio, razao));
        }
        
        ControleUsuario.exibirMensagemCarga(1); 
    }

    public static Investidor buscarInvestidor(String doc) {
        for (Investidor inv : listaInvestidores) {
            if (inv.getDocumento().equals(doc)) {
                return inv; // Encontrou o investidor
            }
        }
        return null; // Percorreu a lista toda e não achou ninguém
    }

    public static void cadastrarInvestidorLote() {
        // ussar primeiro um caminho fixo para teste, depois pedir para o usuario inserir
        String caminho = "resources/Arquivoscsv/investidores.csv"; 
        
        List<Investidor> novos = CarregarCSV.lerInvestidores(caminho);
        
        if (!novos.isEmpty()) {
            listaInvestidores.addAll(novos);
            ControleUsuario.exibirMensagemCarga(novos.size());
        } else {
            ControleUsuario.exibirErroCustomizado("Nao foi possivel carregar o lote de investidores.");
        }
    } 
    public static void excluirInvestidores(){

    }

    public static void listarInvestidores() {
    // Se a lista estiver vazia, avisa a view sem usar Strings aqui
        if (listaInvestidores.isEmpty()) {
            ControleUsuario.exibirErroCustomizado("Nenhum investidor no sistema.");
            return;
        }
        
        // O sistema apenas repassa a lista completa para a view tratar a exibição
        ControleUsuario.exibirListaInvestidores(listaInvestidores);
    }


    
    // Variável de sessão para o investidor atual
    private static Investidor investidorLogado = null;

    public static void selecionarInvestidor() {
        String doc = ControleUsuario.lerDocumentoValidado();
        Investidor encontrado = buscarInvestidor(doc); 

        if (encontrado != null) {
            investidorLogado = encontrado;
            ControleUsuario.exibirMensagemCarga(1); // Sucesso
            
            // AQUI ESTÁ O ERRO: Você precisa chamar o menu novo!
            new view.Menus.MenuInvestidorSelected().executar(); 
            
            investidorLogado = null; // Desloga ao sair
        } else {
            ControleUsuario.exibirErroCustomizado("Investidor não encontrado.");
        }
    }

    /**
     * Regra de Negócio: Verifica se o investidor logado tem permissão para o ativo.
     */
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
            if (num == 0 && a instanceof model.Acao) filtrados.add(a);
            else if (num == 1 && a instanceof model.FII) filtrados.add(a);
            else if (num == 2 && a instanceof model.Criptoativo) filtrados.add(a);
            else if (num == 3 && a instanceof model.Stock) filtrados.add(a);
            else if (num == 4 && a instanceof model.Tesouro) filtrados.add(a);
        }

        if (filtrados.isEmpty()) {
            ControleUsuario.exibirErroCustomizado("Nenhum ativo encontrado para esta categoria.");
        } else {
            ControleUsuario.exibirTabelaAtivos(filtrados);
        }
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
    public static void editarInfoInvestidor(){
        
    }
    public static void excluirInvestidor(){

    }
    public static void exibirAtivosInvestidor(){

    }
    public static void exibirValorTotalGasto(){

    }
    public static void exibirValorTotalAtual(){

    }
    public static void porcentRendas(){

    }
    public static void porcentProdutos(){

    }

    public static void adicionarMovCompra(){

    }
    public static void adicionarMovVenda(){

    }
    public static void adicionarLoteMov(){

    }
}