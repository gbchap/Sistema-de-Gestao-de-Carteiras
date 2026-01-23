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
            InterfaceUsuario.exibirMensagemCarga(bancoDeAtivos.size());
        } catch (Exception e) {
            InterfaceUsuario.exibirErroCustomizado(e.getMessage());
        }

        InterfaceUsuario.exibirBoasVindas();
        new MenuPrincipal().executar();
    }

    
    public static void cadastrarAtivo(){
        // To-Do 
    }

    public static void cadastrarInvestidor() {
        String nome;
        while (true) {
            nome = InterfaceUsuario.lerNome();
            int statusNome = Validador.validarTexto(nome);
            if (statusNome == 0) break;
            InterfaceUsuario.exibirMensagemErroValidador(statusNome); // Exibe erro -11
        }
        //validação da entrada do documento:
        String docRaw; // Declarada aqui para ser usada no loop
        int statusDoc;
        while (true) {
            docRaw = InterfaceUsuario.lerDocumento();
            statusDoc = Validador.validarDocumento(docRaw);

            if (statusDoc == 0) {
            // Se o formato estiver ok, agora conferimos a duplicidade (To-Do -6)
            String docLimpoTemp = Validador.limparDocumento(docRaw);
            if (buscarInvestidor(docLimpoTemp) != null) {
                InterfaceUsuario.exibirMensagemErroValidador(-6);
                continue;
            }
            break; 
            } else {
                InterfaceUsuario.exibirMensagemErroValidador(statusDoc);
            }
        }

        String docLimpo = Validador.limparDocumento(docRaw);
        double patrimonio = InterfaceUsuario.lerPatrimonio();
        
        if (Validador.isCPF(docLimpo)) { 
            String perfil = InterfaceUsuario.lerPerfil();
            listaInvestidores.add(new PessoaFisica(nome, docLimpo, patrimonio, perfil));
        } else { 
            String razao = InterfaceUsuario.lerRazaoSocial();
            listaInvestidores.add(new Institucional(nome, docLimpo, patrimonio, razao));
        }
        
        InterfaceUsuario.exibirMensagemCarga(1); 
    }

    public static Investidor buscarInvestidor(String doc) {
    for (Investidor inv : listaInvestidores) {
        if (inv.getDocumento().equals(doc)) {
            return inv; // Encontrou o investidor
        }
    }
    return null; // Percorreu a lista toda e não achou ninguém
}

    public static void listarInvestidores() {
    // Se a lista estiver vazia, avisa a view sem usar Strings aqui
    if (listaInvestidores.isEmpty()) {
        InterfaceUsuario.exibirErroCustomizado("Nenhum investidor no sistema.");
        return;
    }
    
    // O sistema apenas repassa a lista completa para a view tratar a exibição
    InterfaceUsuario.exibirListaInvestidores(listaInvestidores);
}
    
}