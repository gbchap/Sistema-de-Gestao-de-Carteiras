package model;

public class PessoaFisica extends Investidor {
    private String perfil; //Sobre os investidores Pessoa Física: Conservador, Moderado ou Arrojado [cite: 126]

    public PessoaFisica(String nome, String cpf, double patrimonio, String perfil) {
        super(nome, cpf, patrimonio);
        this.perfil = perfil;
    }

    public String getPerfil() { return perfil; }
}