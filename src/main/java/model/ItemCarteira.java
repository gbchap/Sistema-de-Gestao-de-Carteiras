package model;

import model.Ativos.Ativo;

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
    
    public void setQuantidade(double quantidade) {
        if (quantidade >= 0){
            this.quantidade = quantidade;
        }else{
            throw new IllegalArgumentException("Quantidade não pode ser negativa ou vazia.");
        }
    }

    public void setPrecoMedio(double precoMedio) { 
        if(precoMedio >= 0){
            this.precoMedio = precoMedio;
        }else{
            throw new IllegalArgumentException("Preço médio não pode ser negativo ou vazio.");
        }
    }
}