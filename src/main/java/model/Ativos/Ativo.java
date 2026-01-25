package model.Ativos;

import view.ControleUsuario;

/**
 * Classe abstrata que representa a base de qualquer ativo financeiro.
 * Não pode ser instanciada diretamente.
 */
public abstract class Ativo {
    private String nome;
    private String ticker;
    private double precoAtual;
    private boolean qualificado; 
    
    public Ativo(String nome, String ticker, double precoAtual, boolean qualificado) {
        if (ticker == null || ticker.trim().isEmpty()) {
            throw new IllegalArgumentException("O ticker do ativo não pode ser nulo ou vazio.");
        }
        if (precoAtual < 0) {
            throw new IllegalArgumentException("O preço do ativo não pode ser negativo.");
        }
        
        this.nome = nome;
        this.ticker = ticker;
        this.precoAtual = precoAtual;
        this.qualificado = qualificado;
    }
    public double getPrecoEmReais() {
        return this.precoAtual;
    }

    // getters e setters 
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        if(nome != null && !nome.isEmpty()){
            this.nome = nome;
        } else {
            ControleUsuario.exibirErroCustomizado("Nome inválido.");
        }
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker){
        if(ticker != null && !ticker.isEmpty()){
            this.ticker = ticker;
        } else {
            ControleUsuario.exibirErroCustomizado("Ticker inválido.");
        }
    }

    public double getPrecoAtual() {
        return precoAtual;
    }


    public void setPrecoAtual(double precoAtual) {
        if (precoAtual < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.precoAtual = precoAtual;
    }

    public boolean isQualificado() {
        return qualificado;
    }

    public void setQualificado(boolean qualificado) {
        if(qualificado != true && qualificado != false){
            ControleUsuario.exibirErroCustomizado("Valor inválido para qualificado.");
        }
        this.qualificado = qualificado;
    }

    /**
     * Método abstrato para identificar se o ativo é de Renda Fixa ou Variável.
     * Deve ser implementado pelas subclasses
     */
    public abstract String getTipoRenda();

    @Override
    public String toString() {
        return String.format("Ativo: %s (%s) | Preço: %.2f | Restrito: %s", 
                nome, ticker, precoAtual, qualificado ? "Sim" : "Não");
    }
}