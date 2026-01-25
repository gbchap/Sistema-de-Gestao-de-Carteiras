package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;

public class MenuPrincipal extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        ControleUsuario.exibirMenuPrincipal();
    }

    @Override
    public void executar() {
        super.executar(); 
        ControleUsuario.exibirEncerrando(); 
    }
    
    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: 
                new MenuAtivos().executar(); 
                break; 
            case 2: 
                new MenuInvestidores().executar();
                break; 
            default: 
                throw new OpcaoInvalidaException(opcao);
        }
    }   
}