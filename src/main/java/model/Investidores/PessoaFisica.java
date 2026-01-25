package model.Investidores;


public class PessoaFisica extends Investidor {
    private String perfil; //Sobre os investidores Pessoa Física: Conservador, Moderado ou Arrojado
    public PessoaFisica(String nome, String cpf, String telefone, String dataNasc, String endereco, double patrimonio, String perfil) {
        super(nome, cpf, telefone, dataNasc, endereco, patrimonio);
        this.perfil = perfil;
    }

    public String getPerfil() { return perfil; }
}