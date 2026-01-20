package model;

public class ItemCarteira {
    private Ativo ativo;
    private double quantidade;

    public ItemCarteira(Ativo ativo, double quantidade) {
        this.ativo = ativo;
        this.quantidade = quantidade;
    }

    public Ativo getAtivo() { return ativo; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
}