package model.Investidores;

import java.util.ArrayList;
import java.util.List;

import model.Carteira;
import model.ItemCarteira;
import model.Movimentacao;

public abstract class Investidor {
    private String nome;
    private String documento; // para o CPF ou CNPJ 
    private double patrimonioTotal; // Em Real [tipo : 113]
    protected Carteira carteira;
    protected List<Movimentacao> historico;

    public Investidor(String nome, String documento, double patrimonioTotal) {
        this.nome = nome;
        this.documento = documento;
        this.patrimonioTotal = patrimonioTotal;
        this.carteira = new Carteira();
        this.historico = new ArrayList<>();
    }

    public void cadastrarInvestimento(Movimentacao m) {
        this.historico.add(m);
        // TODO - Atualizar a carteira conforme a movimentação
    }

    // getters e setters
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public double getPatrimonioTotal() { return patrimonioTotal; }
    public Carteira getCarteira() { return carteira; }
    
    public void setPatrimonioTotal(double patrimonio) {
        if (patrimonio < 0) throw new IllegalArgumentException("Patrimônio não pode ser negativo.");
        this.patrimonioTotal = patrimonio;
    }

    // esse método é para verificar se é investidor qualificado (>= 1 milhão) (só uma validação)
    public boolean isQualificado() {
        return this.patrimonioTotal >= 1000000.0;
    }

}