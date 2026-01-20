package model;

public class ItemCarteira {
    private Ativo ativo;
    private double quantidade;
    private double precoMedio;

    public ItemCarteira(Ativo ativo, double quantidade, double precoMedio) {
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.precoMedio = precoMedio;
    }

    public Ativo getAtivo() { return ativo; }
    public double getQuantidade() { return quantidade; }
    public double getPrecoMedio() { return precoMedio; }
    
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public void setPrecoMedio(double precoMedio) { this.precoMedio = precoMedio; }
}