package model;

public class Cliente {
    private int idCliente;
    private String nomeCliente;
    private String senha;
    private String cpfCliente;
    private String enderecoClietne;
    private String telefoneCliente;

    public Cliente(int idCliente, String nomeCliente, String senha, String cpfCliente, String enderecoClietne,
    String telefoneCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.senha = senha;
        this.cpfCliente = cpfCliente;
        this.enderecoClietne = enderecoClietne;
        this.telefoneCliente = telefoneCliente;
    }
    
    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getEnderecoClietne() {
        return enderecoClietne;
    }

    public void setEnderecoClietne(String enderecoClietne) {
        this.enderecoClietne = enderecoClietne;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }


}
