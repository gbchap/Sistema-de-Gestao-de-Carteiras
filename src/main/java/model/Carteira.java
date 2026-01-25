package model;

import java.util.ArrayList;
import java.util.List;
import model.Ativos.*;

public class Carteira {
    private List<ItemCarteira> itens;

    public Carteira() {
        this.itens = new ArrayList<>();
    }

    public List<ItemCarteira> getItens() { return itens; }

    public void adicionarItem(ItemCarteira item) {
        this.itens.add(item);
    }

    
    public void removerAtivoPorTicker(String ticker) {
        itens.removeIf(item -> item.getAtivo().getTicker().equalsIgnoreCase(ticker));
    }

    
    public double getValorTotalEmReais() {
        double total = 0;
        for (ItemCarteira item : itens) {
            total += item.getQuantidade() * item.getAtivo().getPrecoEmReais();
        }
        return total;
    }

    
    public double getPercentualRendaFixa() {
        double totalGeral = getValorTotalEmReais();
        if (totalGeral == 0) return 0;
        double rf = 0;
        for (ItemCarteira item : itens) {
            if (item.getAtivo() instanceof Tesouro) {
                rf += item.getQuantidade() * item.getAtivo().getPrecoEmReais();
            }
        }
        return (rf / totalGeral) * 100;
    }

        public double getPercentualRendaVariavel() {
        return 100 - getPercentualRendaFixa(); 
    }

    
    public double getPercentualNacional() {
        double totalGeral = getValorTotalEmReais();
        if (totalGeral == 0) return 0;
        double nac = 0;
        for (ItemCarteira item : itens) {
            Ativo a = item.getAtivo();
            if (a instanceof Acao || a instanceof FII || a instanceof Tesouro) {
                nac += item.getQuantidade() * a.getPrecoEmReais();
            }
        }
        return (nac / totalGeral) * 100;
    }
    
    public double getPercentualInternacional() {
        return 100 - getPercentualNacional();
    }
}