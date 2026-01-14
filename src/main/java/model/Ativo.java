package model;

/**
 * Classe abstrata que representa a base de qualquer ativo financeiro.
 * Não pode ser instanciada diretamente.
 */
public abstract class Ativo {
    private String nome;
    private String ticker;
    private double precoAtual;
    private boolean qualificado; // Indica se é restrito a investidores qualificados [cite: 86]

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

    // Getters e Setters 
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrecoAtual() {
        return precoAtual;
    }

    /**
     * Altera o preço do ativo, garantindo que não seja negativo[cite: 38].
     */
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