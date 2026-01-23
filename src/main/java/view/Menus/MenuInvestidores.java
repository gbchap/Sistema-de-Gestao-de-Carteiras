package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.InterfaceUsuario;
import view.SistemaGestao;

public class MenuInvestidores extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        InterfaceUsuario.exibirMenuInvestidores();
    }

    @Override
    public void executar() {
        super.executar();
        new MenuPrincipal().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: SistemaGestao.cadastrarInvestidor();
            case 2: 
            case 3: SistemaGestao.listarInvestidores(); 
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}

