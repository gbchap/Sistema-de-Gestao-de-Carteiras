package view.Menus;

import Exceptions.OpcaoInvalidaException;
import view.InterfaceUsuario;

public abstract class MenuBase {
    protected abstract void exibirOpcoes();
    protected abstract void processarEscolha(int opcao) throws OpcaoInvalidaException;

    public void executar(){
        int opcao = -1;
        while (opcao !=0){
            exibirOpcoes();
            opcao = InterfaceUsuario.lerOpcao(); 

            try{
                if (opcao == -1){
                    InterfaceUsuario.exibirErroEntrada();           
                } else if (opcao != 0) {
                    processarEscolha(opcao);
                }
            }catch (OpcaoInvalidaException e){
                InterfaceUsuario.exibirErroCustomizado(e.getMessage());
            }
        }
    }
}
