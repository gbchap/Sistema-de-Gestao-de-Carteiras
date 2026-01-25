package model.Ativos;

import model.TipoAtivo;

public class Acao extends Ativo implements TipoAtivo {
    public Acao(String nome, String ticker, double precoAtual, boolean qualificado) {
        super(nome, ticker, precoAtual, qualificado);
    }

    public String getTipoAcao() {
        if (getTicker().endsWith("3")) return "Ordinária";
        if (getTicker().endsWith("11")) return "Unit";
        return "Preferencial";
    }

    @Override
    public String getTipoRenda() { return "Renda Variável"; }

    @Override
    public String formatar(double valor) {
        return String.format("R$ %.2f", valor);
    }
}