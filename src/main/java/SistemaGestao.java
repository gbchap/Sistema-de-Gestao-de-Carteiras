
import java.util.ArrayList;
import java.util.List;

import Exceptions.OpcaoInvalidaException;
import resources.CarregarCSV;
import view.InterfaceUsuario;
import model.*;

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
        String nome = InterfaceUsuario.lerNome();
        String doc = InterfaceUsuario.lerDocumento();
        double patrimonio = InterfaceUsuario.lerPatrimonio();
        
        // Logica de decisão baseada no tamanho do documento ( é a regra de negócio)
        if (doc.length() <= 14) { 
            String perfil = InterfaceUsuario.lerPerfil();
            listaInvestidores.add(new PessoaFisica(nome, doc, patrimonio, perfil));
        } else {
            String razao = InterfaceUsuario.lerRazaoSocial();
            listaInvestidores.add(new Institucional(nome, doc, patrimonio, razao));
        }
        
        InterfaceUsuario.exibirMensagemCarga(1); 
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