package model;

import java.util.ArrayList;
import java.util.List;

import model.Ativos.*;

/**
 * Representa a carteira de investimentos de um investidor.
 * Esta é uma das classes mais complexas do sistema, responsável por consolidar 
 * os ativos, realizar conversões de moeda e calcular a alocação de patrimônio.
 */
public class Carteira {
    private List<ItemCarteira> itens;

    public Carteira() {
        this.itens = new ArrayList<>();
    }

    /**
     * Remove um ativo da carteira baseado no ticker.
     * REQUISITO: Exclusão em cascata. Quando um ativo é deletado do sistema,
     * este método garante que ele suma de todas as carteiras que o possuem.
     */
    public void removerAtivoPorTicker(String ticker) {
        if (ticker != null && itens != null) {
            itens.removeIf(item -> item.getAtivo().getTicker().equalsIgnoreCase(ticker));
        }
    }

    public void adicionarItem(ItemCarteira item) {
        this.itens.add(item);
    }

    /**
     * Calcula o valor total da carteira em Reais (BRL).
     * REQUISITO: Conversão de moeda. Utiliza getPrecoEmReais() definido na classe Ativo.
     */
    public double getValorTotalEmReais() {
        double total = 0;
        for (ItemCarteira item : itens) {
            total += item.getAtivo().getPrecoEmReais() * item.getQuantidade();
        }
        return total;
    }

    /**
     * Calcula a porcentagem de Renda Fixa na carteira.
     */
    public double getPercentualRendaFixa() {
        double totalGeral = getValorTotalEmReais();
        if (totalGeral == 0) return 0;

        double totalRendaFixa = 0;
        for (ItemCarteira item : itens) {
            if ("Renda Fixa".equalsIgnoreCase(item.getAtivo().getTipoRenda())) {
                totalRendaFixa += item.getAtivo().getPrecoEmReais() * item.getQuantidade();
            }
        }
        return (totalRendaFixa / totalGeral) * 100;
    }

    /**
     * Calcula a porcentagem de Renda Variável na carteira.
     */
    public double getPercentualRendaVariavel() {
        double totalGeral = getValorTotalEmReais();
        if (totalGeral == 0) return 0;

        double totalRendaVariavel = 0;
        for (ItemCarteira item : itens) {
            if ("Renda Variável".equalsIgnoreCase(item.getAtivo().getTipoRenda())) {
                totalRendaVariavel += item.getAtivo().getPrecoEmReais() * item.getQuantidade();
            }
        }
        return (totalRendaVariavel / totalGeral) * 100;
    }

    /**
     * Calcula a porcentagem de ativos internacionais (Stocks e Criptos).
     * REQUISITO: Relatórios de Nacionalidade.
     */
    public double getPercentualInternacional() {
        double totalGeral = getValorTotalEmReais();
        if (totalGeral == 0) return 0;

        double totalInternacional = 0;
        for (ItemCarteira item : itens) {
            Ativo a = item.getAtivo();
            // Verifica se é uma das classes internacionais para o relatório
            if (a instanceof Stock || a instanceof Criptoativo) {
                totalInternacional += a.getPrecoEmReais() * item.getQuantidade();
            }
        }
        return (totalInternacional / totalGeral) * 100;
    }

    /**
     * Calcula a porcentagem de ativos nacionais.
     */
    public double getPercentualNacional() {
        if (getValorTotalEmReais() == 0) return 0;
        return 100.0 - getPercentualInternacional();
    }

    public List<ItemCarteira> getItens() {
        return itens;
    }
}