package Exceptions;

public class OpcaoInvalidaException extends Exception{
    public OpcaoInvalidaException(int opcao){
        super("A opcão " + opcao + " não existe no menu.");
    }
}
