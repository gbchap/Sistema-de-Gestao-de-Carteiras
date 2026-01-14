
import java.util.ArrayList;
import java.util.List;

import model.Ativo;
import Exceptions.OpcaoInvalidaException;
import resources.CarregarCSV;
import view.InterfaceUsuario;

public class SistemaGestao {
    private static List<Ativo> bancoDeAtivos = new ArrayList<>();

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
            case 2: /* gerenciarInvestidores(); */ break;
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
}