package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Investidor {
    private String nome;
    private String documento; // para o CPF ou CNPJ 
    private double patrimonioTotal; // Em Real [tipo : 113]
    // A Carteira vai ser uma lista de itens :Ativo + Quantidade
    protected List<ItemCarteira> carteira; 

    public Investidor(String nome, String documento, double patrimonioTotal) {
        this.nome = nome;
        this.documento = documento;
        this.patrimonioTotal = patrimonioTotal;
        this.carteira = new ArrayList<>();
    }

    // getters e setters
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public double getPatrimonioTotal() { return patrimonioTotal; }
    
    public void setPatrimonioTotal(double patrimonio) {
        if (patrimonio < 0) throw new IllegalArgumentException("Patrimônio não pode ser negativo.");
        this.patrimonioTotal = patrimonio;
    }

    // esse método é para verificar se é investidor qualificado (>= 1 milhão) (só uma validação)
    public boolean isQualificado() {
        return this.patrimonioTotal >= 1000000.0;
    }

    public List<ItemCarteira> getCarteira() { return carteira; }
}