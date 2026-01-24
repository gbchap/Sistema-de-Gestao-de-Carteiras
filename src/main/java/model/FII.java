package model;

public class FII extends Ativo implements TipoAtivo {
    private String segmento;
    private double ultimoDividendo;
    private double taxaAdministracao;

    public FII(String nome, String ticker, double precoAtual, boolean qualificado, 
               String segmento, double ultimoDividendo, double taxaAdministracao) {
        super(nome, ticker, precoAtual, qualificado);
        this.segmento = segmento;
        this.ultimoDividendo = ultimoDividendo;
        this.taxaAdministracao = taxaAdministracao;
    }

    public String getTaxaFormatada() {
        return taxaAdministracao + "%";
    }

    @Override
    public String formatar(double valor) {
        return String.format("R$ %.2f", valor);
    }

    @Override
    public String getTipoRenda() { return "Renda Variável"; }
}