package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.InterfaceUsuario;

public class MenuPrincipal extends MenuBase{
    @Override
    protected void exibirOpcoes() {
        InterfaceUsuario.exibirMenuPrincipal();
    }

     @Override
    public void executar() {
        super.executar();
        InterfaceUsuario.exibirEncerrando();
    }
    
    @Override
    protected void processarEscolha(int opcao) throws Exceptions.OpcaoInvalidaException {
        switch (opcao) {
            case 1: new MenuAtivos().executar(); 
            case 2: new MenuInvestidores().executar(); 
            default: throw new OpcaoInvalidaException(opcao);
        }
    }   
    
}
