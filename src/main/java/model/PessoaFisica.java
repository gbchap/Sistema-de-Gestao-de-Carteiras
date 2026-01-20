package model;

import java.time.LocalDate;

public class PessoaFisica extends Investidor {
    private String perfil; //Sobre os investidores Pessoa Física: Conservador, Moderado ou Arrojado [cite: 126]
    private LocalDate dataNascimento;
    public PessoaFisica(String nome, String cpf, double patrimonio, String perfil) {
        super(nome, cpf, patrimonio);
        this.perfil = perfil;
        this.dataNascimento = dataNascimento;
    }

    public String getPerfil() { return perfil; }
    public LocalDate getDataNascimento() { return dataNascimento; }
}