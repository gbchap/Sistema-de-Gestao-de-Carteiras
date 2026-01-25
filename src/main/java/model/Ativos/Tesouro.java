package model.Ativos;


import model.TipoAtivo;

public class Tesouro extends Ativo implements TipoAtivo{
    private String tipoRendimento; // Selic, Prefixado ou IPCA+
    private String dataVencimento;

    public Tesouro(String nome, String ticker, double precoAtual, boolean qualificado, 
                   String tipoRendimento, String dataVencimento) {
        super(nome, ticker, precoAtual, qualificado);
        this.tipoRendimento = tipoRendimento;
        this.dataVencimento = dataVencimento;
    }

    @Override
    public String formatar(double valor) {
        return String.format("R$ %.2f", valor);
    }

    @Override
    public String getTipoRenda() { return "Renda Fixa"; }
}