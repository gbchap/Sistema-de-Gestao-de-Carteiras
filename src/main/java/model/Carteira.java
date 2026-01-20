package model;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private List<ItemCarteira> itens;

    public Carteira() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemCarteira item) {
        this.itens.add(item);
    }

    public double getValorTotal() {
        double total = 0;
        for (ItemCarteira item : itens) {
            // Valor atual do ativo multiplicado pela quantidade que o investidor possui
            total += item.getAtivo().getPrecoAtual() * item.getQuantidade();
        }
        return total;
    }

    public double getPercentualRendaFixa() {
        double totalGeral = getValorTotal();
        if (totalGeral == 0) return 0;

        double totalRendaFixa = 0;
        for (ItemCarteira item : itens) {
            if (item.getAtivo().getTipoRenda().equals("Renda Fixa")) {
                totalRendaFixa += item.getAtivo().getPrecoAtual() * item.getQuantidade();
            }
        }
        return (totalRendaFixa / totalGeral) * 100;
    }

    public List<ItemCarteira> getItens() {
        return itens;
    }
}