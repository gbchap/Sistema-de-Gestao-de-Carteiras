package model.Investidores;

import java.util.ArrayList;
import java.util.List;

import model.Ativos.Ativo;
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
    public void comprarAtivo(Ativo ativo, double quantidade, double preco) {
        // 1. Atualiza a Carteira
        ItemCarteira novoItem = new ItemCarteira(ativo, (int)quantidade, preco);
        this.carteira.adicionarItem(novoItem);
        
        // 2. Gera um ID Único simples para a movimentação (ex: M + timestamp)
        String idMov = "M" + System.currentTimeMillis();
        
        // 3. Registra usando a SUA classe Movimentacao
        Movimentacao m = new Movimentacao(idMov, ativo, quantidade, preco, "Compra");
        this.historico.add(m);
    }
    public boolean venderAtivo(String ticker, double quantidade) {
        for (ItemCarteira item : carteira.getItens()) {
            if (item.getAtivo().getTicker().equalsIgnoreCase(ticker)) {
                // TRAVA: Verifica se a quantidade em carteira é suficiente
                if (item.getQuantidade() >= quantidade) {
                    item.setQuantidade(item.getQuantidade() - quantidade);
                    this.historico.add(new Movimentacao("V" + System.currentTimeMillis(), 
                                        item.getAtivo(), quantidade, item.getAtivo().getPrecoAtual(), "Venda"));
                    return true;
                }
            }
        }
        return false;
    }

}