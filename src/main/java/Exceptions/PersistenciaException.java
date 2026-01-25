package Exceptions;
import java.io.IOException;

public class PersistenciaException extends IOException {
    public PersistenciaException(String mensagem) {
        super("[ERRO DE ARQUIVO]: " + mensagem);
    }
}