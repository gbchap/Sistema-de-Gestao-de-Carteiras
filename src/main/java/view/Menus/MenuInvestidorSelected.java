package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;
import view.SistemaGestao;

public class MenuInvestidorSelected extends MenuBase {
    @Override
    protected void exibirOpcoes() {
        ControleUsuario.exibirMenuInvestidores();
    }

    @Override
    public void executar() {
        super.executar();
        new MenuInvestidores().executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: SistemaGestao.editarInfoInvestidor();
            case 2: SistemaGestao.excluirInvestidor();
            case 3: SistemaGestao.exibirAtivosInvestidor();
            case 4: SistemaGestao.exibirValorTotalGasto();  
            case 5: SistemaGestao.exibirValorTotalAtual(); 
            case 6: SistemaGestao.porcentRendas();   
            case 7: SistemaGestao.porcentProdutos();
            case 8: SistemaGestao.salvarRelatorio();
            case 9: SistemaGestao.adicionarMovCompra();
            case 10: SistemaGestao.adicionarMovVenda(); 
            case 11: SistemaGestao.adicionarLoteMov();
            default: throw new OpcaoInvalidaException(opcao);
        }
    }
}
