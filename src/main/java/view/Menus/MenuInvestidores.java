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
            case 1: SistemaGestao.cadastrarInvestidor();
            case 2: SistemaGestao.cadastrarInvestidorLote();    
            case 3: SistemaGestao.listarInvestidores(); 
            case 4: SistemaGestao.excluirInvestidores();
            case 5: new MenuInvestidorSelected().executar();
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}

