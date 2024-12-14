package model;

import java.time.LocalDate;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private LocalDate dtValidade;
    private String fornecedor;
    private String descricao;
    private double preco;
    
    public Produto(int idProduto, String nomeProduto, LocalDate dtValidade, String fornecedor, String descricao,
    double preco) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.dtValidade = dtValidade;
        this.fornecedor = fornecedor;
        this.descricao = descricao;
        this.preco = preco;
    }
    
    public Produto() {
    }

    public int getIdProduto() {
        return idProduto;
    }

    @Override
    public String toString() {
        return nomeProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }
    
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public LocalDate getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(LocalDate dtValidade) {
        this.dtValidade = dtValidade;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
