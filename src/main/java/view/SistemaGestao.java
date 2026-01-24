package view;

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

    public static void cadastrarAtivo(){
        String nome;
        while(true){
            nome = ControleUsuario.lerNome();
            int statusNome = Validador.validarTexto(nome);
            if (statusNome == 0) break;
            ControleUsuario.exibirMensagemErroValidador(statusNome);
        }
        String ticker;
        while(true){
            ticker = ControleUsuario.lerTicker();
            int statusTicker = Validador.validarTexto(ticker);
            if (statusTicker == 0) break;
            ControleUsuario.exibirMensagemErroValidador(statusTicker);
        }
        double precoAtual = ControleUsuario.lerPrecoAtual();
        boolean qualificado = ControleUsuario.lerQualificado();

        listaAtivos.add(new Ativo(nome, ticker, precoAtual, qualificado)); //resolver lista
        
        ControleUsuario.exibirMensagemCarga(1); 
    }

    public static void cadastrarAtivoLote(){

    }

    public static void editaAtivo(){

    }

    public static void excluiAtivo(){

    }

    public static void exibirAtivos(int num){

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

    public static void cadastrarInvestidorLote(){

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

//###############################  MENU INVESTIDOR SELECIONADO ################################### 


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
    public static void salvarRelatorio(){

    }
    public static void adicionarMovCompra(){

    }
    public static void adicionarMovVenda(){

    }
    public static void adicionarLoteMov(){

    }
}