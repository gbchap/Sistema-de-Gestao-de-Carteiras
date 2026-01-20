package model;

public class Stock extends Ativo {
    private String bolsa;
    private String setor;
    private double fatorConversao; // Ex: 5.39 PARA DOLAR

    public Stock(String nome, String ticker, double precoAtualMoedaOriginal, boolean qualificado, 
                 String bolsa, String setor, double fatorConversao) {
        super(nome, ticker, precoAtualMoedaOriginal, qualificado);
        this.bolsa = bolsa;
        this.setor = setor;
        this.fatorConversao = fatorConversao;
    }

    public double getPrecoEmReais() {
        return getPrecoAtual() * fatorConversao;
    }

    @Override
    public String getTipoRenda() { return "Renda Variável"; }
}