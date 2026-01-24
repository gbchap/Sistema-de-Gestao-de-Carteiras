package model;

public class Criptoativo extends Ativo implements TipoAtivo{
    private String algoritmoConsenso;
    private String quantidadeMaxima;
    private double fatorConversao;

    public Criptoativo(String nome, String ticker, double precoUSD, boolean qualificado, 
                       String algoritmo, String qtdMax, double fatorConversao) {
        super(nome, ticker, precoUSD, qualificado);
        this.algoritmoConsenso = algoritmo;
        this.quantidadeMaxima = qtdMax;
        this.fatorConversao = fatorConversao;
    }

    @Override
    public String formatar(double valor) {
        return String.format("$ %.2f", valor);
    }

    public double getPrecoEmReais() {
        return getPrecoAtual() * fatorConversao;
    }

    @Override
    public String getTipoRenda() { return "Renda Variável"; }
}