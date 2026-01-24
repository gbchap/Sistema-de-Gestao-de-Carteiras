package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;
import view.SistemaGestao;

public class MenuInvestidores extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        ControleUsuario.exibirMenuInvestidores();
    }

    @Override
    public void executar() {
        super.executar();
        new MenuPrincipal().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: SistemaGestao.cadastrarInvestidor();break;
            case 2: SistemaGestao.cadastrarInvestidorLote();break;
            case 3: SistemaGestao.listarInvestidores(); break;
            case 4: SistemaGestao.excluirInvestidores();break;
            case 5: SistemaGestao.selecionarInvestidor(); break;
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}

