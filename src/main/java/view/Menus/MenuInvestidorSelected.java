package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.InterfaceUsuario;
import view.SistemaGestao;

public class MenuInvestidorSelected extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        InterfaceUsuario.exibirMenuInvestidores();
    }

    @Override
    public void executar() {
        super.executar();
        new MenuInvestidores().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1:
            case 2: 
            case 3: 
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}
