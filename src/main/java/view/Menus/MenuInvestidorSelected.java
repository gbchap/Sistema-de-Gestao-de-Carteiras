package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;
import view.SistemaGestao;

public class MenuInvestidorSelected extends MenuBase {

    @Override
    protected void exibirOpcoes() {
        ControleUsuario.exibirMenuInvestidorSelecionado();
    }

    @Override
    public void executar() {
        super.executar();
    }

    @Override
    protected void processarEscolha(int opcao) throws OpcaoInvalidaException {
        switch (opcao) {
            case 1: 
                SistemaGestao.editarInfoInvestidor(); 
                break;
            case 2: 
                SistemaGestao.excluirInvestidor(); 
                break;
            case 3: 
                SistemaGestao.exibirAtivosInvestidor(); 
                break;
            case 4: 
                SistemaGestao.exibirValorTotalGasto(); 
                break;
            case 5: 
                SistemaGestao.exibirValorTotalAtual(); 
                break;
            case 6: 
                SistemaGestao.porcentRendas(); 
                break;
            case 7: 
                SistemaGestao.porcentProdutos(); 
                break;
            case 8: 
                SistemaGestao.salvarRelatorio();
                break;
            case 9: 
                SistemaGestao.adicionarMovCompra(); 
                break;
            case 10: 
                SistemaGestao.adicionarMovVenda(); 
                break;
            case 11: 
                SistemaGestao.adicionarLoteMov(); 
                break;
            case 0:
                
                break;
            default: 
                throw new OpcaoInvalidaException(opcao);
        }
    }
}