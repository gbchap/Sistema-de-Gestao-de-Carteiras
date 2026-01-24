package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.ControleUsuario;

public abstract class MenuBase {
    protected abstract void exibirOpcoes();
    protected abstract void processarEscolha(int opcao) throws OpcaoInvalidaException;

    public void executar(){
        int opcao = -1;
        while (opcao !=0){
            exibirOpcoes();
            opcao = ControleUsuario.lerOpcao(); 

            try{
                if (opcao == -1){
                    ControleUsuario.exibirErroEntrada();           
                } else if (opcao != 0) {
                    processarEscolha(opcao);
                }
            }catch (OpcaoInvalidaException e){
                ControleUsuario.exibirErroCustomizado(e.getMessage());
            }
        }
    }
}
