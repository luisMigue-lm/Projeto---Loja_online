package model;

import java.time.LocalDate;

public class Pagamento {
    private int idFormaPagmnt;
    private String meioPagmnt;
    private double taxaJuros;
    private int quantParcelas;
    private LocalDate data;
    private String descricao;

    public Pagamento(int idFormaPagmnt, String meioPagmnt, double taxaJuros, int quantParcelas, LocalDate data,
            String descricao) {
        this.idFormaPagmnt = idFormaPagmnt;
        this.meioPagmnt = meioPagmnt;
        this.taxaJuros = taxaJuros;
        this.quantParcelas = quantParcelas;
        this.data = data;
        this.descricao = descricao;
    }

    public Pagamento() {
    }

    public int getIdFormaPagmnt() {
        return idFormaPagmnt;
    }

    public void setIdFormaPagmnt(int idFormaPagmnt) {
        this.idFormaPagmnt = idFormaPagmnt;
    }

    public String getMeioPagmnt() {
        return meioPagmnt;
    }

    public void setMeioPagmnt(String meioPagmnt) {
        this.meioPagmnt = meioPagmnt;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public int getQuantParcelas() {
        return quantParcelas;
    }

    public void setQuantParcelas(int quantParcelas) {
        this.quantParcelas = quantParcelas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
