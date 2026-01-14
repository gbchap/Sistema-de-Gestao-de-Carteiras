package model;

public class Acao extends Ativo {
    public Acao(String nome, String ticker, double precoAtual, boolean qualificado) {
        super(nome, ticker, precoAtual, qualificado);
    }

    public String getTipoAcao() {
        if (getTicker().endsWith("3")) return "Ordinária";
        if (getTicker().endsWith("11")) return "Unit";
        return "Preferencial"; // Finais 4, 5 ou 6
    }

    @Override
    public String getTipoRenda() { return "Renda Variável"; }
}