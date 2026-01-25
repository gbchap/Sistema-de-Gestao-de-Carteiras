package model.Investidores;

import java.util.ArrayList;
import java.util.List;

import model.Ativos.Ativo;
import model.Carteira;
import model.ItemCarteira;
import model.Movimentacao;

public abstract class Investidor {
    private String nome;
    private String documento; 
    private double patrimonioTotal; 
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
    }

   
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public double getPatrimonioTotal() { return patrimonioTotal; }
    public Carteira getCarteira() { return carteira; }
    public String getTelefone() { return telefone; }
    public String getDataNascimento() { return dataNascimento; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) {
        if(nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        }else {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }

    public void setTelefone(String telefone) {
        if(telefone != null && !telefone.trim().isEmpty()) {
            this.telefone = telefone;
        }else {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
    }

    public void setEndereco(String endereco) {
        if(endereco != null && !endereco.trim().isEmpty()) {
            this.endereco = endereco;
        }else {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }
    }


    public void setPatrimonioTotal(double patrimonio) {
        if (patrimonio < 0) throw new IllegalArgumentException("Patrimônio não pode ser negativo.");
        this.patrimonioTotal = patrimonio;
    }

    
    public boolean isQualificado() {
        return this.patrimonioTotal >= 1000000.0;
    }

    public void comprarAtivo(Ativo ativo, double quantidade, double precoExecucao, String instituicao) {
        ItemCarteira itemExistente = null;
        for (ItemCarteira item : carteira.getItens()) {
            if (item.getAtivo().getTicker().equalsIgnoreCase(ativo.getTicker())) {
                itemExistente = item;
                break;
            }
        }

        if (itemExistente != null) {
       
            double custoTotalAntigo = itemExistente.getQuantidade() * itemExistente.getPrecoMedio();
            double novoCusto = quantidade * precoExecucao;
            double novaQuantidade = itemExistente.getQuantidade() + quantidade;
            
            itemExistente.setQuantidade(novaQuantidade);
            itemExistente.setPrecoMedio((custoTotalAntigo + novoCusto) / novaQuantidade);

        } else {
            carteira.adicionarItem(new ItemCarteira(ativo, quantidade, precoExecucao));
        }
        this.historico.add(new Movimentacao("C"+System.currentTimeMillis(), ativo, quantidade, precoExecucao, "Compra", instituicao));
    }

    public boolean venderAtivo(String ticker, double quantidade, String instituicao) {
        for (ItemCarteira item : carteira.getItens()) {
            if (item.getAtivo().getTicker().equalsIgnoreCase(ticker)) {
                
                if (item.getQuantidade() >= quantidade) {
                    item.setQuantidade(item.getQuantidade() - quantidade);
                    
                    if (item.getQuantidade() <= 0) {
                        carteira.getItens().remove(item);
                    }
                    
                    this.historico.add(new Movimentacao("V"+System.currentTimeMillis(), 
                            item.getAtivo(), quantidade, item.getAtivo().getPrecoAtual(), "Venda", instituicao));
                    return true;
                }
            }
        }
        return false;
    }
}