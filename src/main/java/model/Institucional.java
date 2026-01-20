package model;

public class Institucional extends Investidor {
    private String razaoSocial;

    public Institucional(String nome, String cnpj, double patrimonio, String razaoSocial) {
        super(nome, cnpj, patrimonio);
        this.razaoSocial = razaoSocial;
    }

    public String getRazaoSocial() { return razaoSocial; }
}
