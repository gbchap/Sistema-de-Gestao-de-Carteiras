package model.Investidores;

public class Institucional extends Investidor {
    private String razaoSocial;

   public Institucional(String nome, String cnpj, String telefone, String dataNasc, String endereco, double patrimonio, String razaoSocial) {
    super(nome, cnpj, telefone, dataNasc, endereco, patrimonio);
    this.razaoSocial = razaoSocial;
}

    public String getRazaoSocial() { 
        return razaoSocial; 
    }
}
