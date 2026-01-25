package model.Ativos;


import model.TipoAtivo;

public class Stock extends Ativo implements TipoAtivo {
    private String bolsa;
    private String setor;
    private double fatorConversao; // 5.39 PARA DOLAR

    public Stock(String nome, String ticker, double precoAtualMoedaOriginal, boolean qualificado, 
                 String bolsa, String setor, double fatorConversao) {
        super(nome, ticker, precoAtualMoedaOriginal, qualificado);
        this.bolsa = bolsa;
        this.setor = setor;
        this.fatorConversao = fatorConversao;
    }


    @Override
    public String formatar(double valor) {
        return String.format("$ %.2f", valor);
    }

  @Override
    public double getPrecoEmReais() {
        return getPrecoAtual() * 5.39; 
    }

    @Override
    public String getTipoRenda() {
        return "Renda Variável";
    }
}