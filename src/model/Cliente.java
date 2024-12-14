package model;

public class Cliente {
    private int idCliente;
    private String nomeCliente;
    private String genero;
    private String cpfCliente;
    private String enderecoClietne;
    private String telefoneCliente;

    public Cliente(int idCliente, String nomeCliente, String genero, String cpfCliente, String enderecoClietne,
            String telefoneCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.genero = genero;
        this.cpfCliente = cpfCliente;
        this.enderecoClietne = enderecoClietne;
        this.telefoneCliente = telefoneCliente;
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return nomeCliente;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
