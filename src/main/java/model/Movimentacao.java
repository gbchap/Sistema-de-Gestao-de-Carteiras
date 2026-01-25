package model;

import java.time.LocalDateTime;

import model.Ativos.Ativo;

public class Movimentacao {
    private String idUnico;
    private Ativo ativo;
    private double quantidade;
    private double precoExecucao;
    private String tipo; // "Compra" ou "Venda"
    private String instituicao;
    private LocalDateTime dataHora;

    public Movimentacao(String idUnico, Ativo ativo, double quantidade, double precoExecucao, String tipo, String instituicao) {
        this.idUnico = idUnico;
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.precoExecucao = precoExecucao;
        this.tipo = tipo;
        this.instituicao = instituicao;
        this.dataHora = LocalDateTime.now();
    }

    // Getters
    public Ativo getAtivo() { return ativo; }
    public double getQuantidade() { return quantidade; }
    public double getPrecoExecucao() { return precoExecucao; }
    public String getTipo() { return tipo; }
    public String getInstituicao() { return instituicao; }
}