package model;

public class Pedidos {
    private int idItensPedidos;
    private int quantdVendida;
    private double precoVenda;
    private Cliente cliente;
    private Produto produto;
    private Pagamento pagamento;
    
    public Pedidos(int idItensPedidos, int quantdVendida, double precoVenda, Cliente cliente, Produto produto,
            Pagamento pagamento) {
        this.idItensPedidos = idItensPedidos;
        this.quantdVendida = quantdVendida;
        this.precoVenda = precoVenda;
        this.cliente = cliente;
        this.produto = produto;
        this.pagamento = pagamento;
    }

    public Pedidos() {
    }

    public int getIdItensPedidos() {
        return idItensPedidos;
    }

    public void setIdItensPedidos(int idItensPedidos) {
        this.idItensPedidos = idItensPedidos;
    }

    public int getQuantdVendida() {
        return quantdVendida;
    }

    public void setQuantdVendida(int quantdVendida) {
        this.quantdVendida = quantdVendida;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }



}
