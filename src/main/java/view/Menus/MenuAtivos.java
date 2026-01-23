package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.InterfaceUsuario;
import view.SistemaGestao;

public class MenuAtivos extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        InterfaceUsuario.exibirMenuAtivos();
    }
    
    @Override
    public void executar() {
        super.executar();
        new MenuPrincipal().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1:
            case 2: 
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}
