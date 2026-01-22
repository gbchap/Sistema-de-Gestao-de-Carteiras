
import java.util.ArrayList;
import java.util.List;
import Exceptions.OpcaoInvalidaException;
import resources.CarregarCSV;
import resources.Validador;
import view.InterfaceUsuario;
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
        executarLoopPrincipal();
    }

    private static void executarLoopPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            InterfaceUsuario.exibirMenuPrincipal();
            opcao = InterfaceUsuario.lerOpcao();

            try {
                if (opcao == 0) {
                    InterfaceUsuario.exibirEncerrando();
                } else if (opcao == -1) {
                    InterfaceUsuario.exibirErroEntrada();
                } else {
                    processarOpcaoPrincipal(opcao);
                }
            } catch (OpcaoInvalidaException e) {
                InterfaceUsuario.exibirErroCustomizado(e.getMessage());
            }
        }
    }

    private static void processarOpcaoPrincipal(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: gerenciarAtivos(); break;
            case 2: gerenciarInvestidores(); break; 
            default: throw new OpcaoInvalidaException(opcao);
        }
    }

    private static void gerenciarAtivos() {
        int opcao = -1;
        while (opcao != 0) {
            InterfaceUsuario.exibirMenuAtivos();
            opcao = InterfaceUsuario.lerOpcao();
            if (opcao == 0) break;
            
            // Lógica de processamento de ativos aqui (Exibir listas, etc)
        }
    }

    private static void gerenciarInvestidores() {
        int opcao = -1;
        while (opcao != 0) {
            InterfaceUsuario.exibirMenuInvestidores();
            opcao = InterfaceUsuario.lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarInvestidor();
                    break;
                case 3:
                    listarInvestidores();
                    break;
                case 0:
                    break;
                default:
                    InterfaceUsuario.exibirErroCustomizado("Opção ainda não implementada.");
            }
        }
    }
    
    private static void cadastrarInvestidor() {
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

    private static Investidor buscarInvestidor(String doc) {
    for (Investidor inv : listaInvestidores) {
        if (inv.getDocumento().equals(doc)) {
            return inv; // Encontrou o investidor
        }
    }
    return null; // Percorreu a lista toda e não achou ninguém
}

    private static void listarInvestidores() {
    // Se a lista estiver vazia, avisa a view sem usar Strings aqui
    if (listaInvestidores.isEmpty()) {
        InterfaceUsuario.exibirErroCustomizado("Nenhum investidor no sistema.");
        return;
    }
    
    // O sistema apenas repassa a lista completa para a view tratar a exibição
    InterfaceUsuario.exibirListaInvestidores(listaInvestidores);
}
    
}