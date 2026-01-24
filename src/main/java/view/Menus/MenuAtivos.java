package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;
import view.SistemaGestao;

public class MenuAtivos extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        ControleUsuario.exibirMenuAtivos();
    }
    
    @Override
    public void executar() {
        super.executar();
        new MenuPrincipal().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: SistemaGestao.cadastrarAtivo(); break;
            case 2: SistemaGestao.cadastrarAtivoLote();break;
            case 3: SistemaGestao.editaAtivo();break;
            case 4: SistemaGestao.excluiAtivo();break;
            case 5, 6, 7, 8, 9, 10: 
                SistemaGestao.exibirAtivos(opcao - 6); 
                break;
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}
