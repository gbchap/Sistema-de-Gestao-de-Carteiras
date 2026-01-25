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
    private String telefone;        
    private String dataNascimento;  
    private String endereco;       
    protected Carteira carteira;
    protected List<Movimentacao> historico;

public Investidor(String nome, String documento, String telefone, String dataNascimento, String endereco, double patrimonioTotal) {
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
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
    public String getTelefone() { return telefone; }
    public String getDataNascimento() { return dataNascimento; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public void setPatrimonioTotal(double patrimonio) {
        if (patrimonio < 0) throw new IllegalArgumentException("Patrimônio não pode ser negativo.");
        this.patrimonioTotal = patrimonio;
    }

    // esse método é para verificar se é investidor qualificado (>= 1 milhão) (só uma validação)
    public boolean isQualificado() {
        return this.patrimonioTotal >= 1000000.0;
    }
public void comprarAtivo(Ativo ativo, double quantidade, double precoExecucao) {
    ItemCarteira itemExistente = null;
    for (ItemCarteira item : carteira.getItens()) {
        if (item.getAtivo().getTicker().equalsIgnoreCase(ativo.getTicker())) {
            itemExistente = item;
            break;
        }
    }

    if (itemExistente != null) {
        // Lógica de Preço Médio: (Qtd Atual * PM Atual + Nova Qtd * Novo Preço) / Qtd Total
        double custoTotalAntigo = itemExistente.getQuantidade() * itemExistente.getPrecoMedio();
        double novoCusto = quantidade * precoExecucao;
        double novaQuantidade = itemExistente.getQuantidade() + quantidade;
        
        itemExistente.setQuantidade(novaQuantidade);
        itemExistente.setPrecoMedio((custoTotalAntigo + novoCusto) / novaQuantidade);
    } else {
        // Se não tinha o ativo, adiciona um novo Item na carteira
        carteira.adicionarItem(new ItemCarteira(ativo, quantidade, precoExecucao));
    }
    
    // Registra no histórico
    this.historico.add(new Movimentacao("C"+System.currentTimeMillis(), ativo, quantidade, precoExecucao, "Compra"));
}

public boolean venderAtivo(String ticker, double quantidade) {
    for (ItemCarteira item : carteira.getItens()) {
        if (item.getAtivo().getTicker().equalsIgnoreCase(ticker)) {
            // TRAVA DE SEGURANÇA: Não vende o que não tem
            if (item.getQuantidade() >= quantidade) {
                item.setQuantidade(item.getQuantidade() - quantidade);
                
                // Se zerar a posição, removemos o item da carteira
                if (item.getQuantidade() <= 0) {
                    carteira.getItens().remove(item);
                }
                
                this.historico.add(new Movimentacao("V"+System.currentTimeMillis(), 
                        item.getAtivo(), quantidade, item.getAtivo().getPrecoAtual(), "Venda"));
                return true;
            }
        }
    }
    return false;
}
}