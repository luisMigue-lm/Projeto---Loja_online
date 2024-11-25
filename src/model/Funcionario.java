package model;

import java.time.LocalDate;

public class Funcionario {
    private int idFuncionario;
    private String nomeFuncionario;
    private int senha;
    private int cpfFuncionario;
    private LocalDate dtNascimento;

    public Funcionario(int idFuncionario, String nomeFuncionario, int senha, int cpfFuncionario,
            LocalDate dtNascimento) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.senha = senha;
        this.cpfFuncionario = cpfFuncionario;
        this.dtNascimento = dtNascimento;
    }
    
    public Funcionario() {
    }
    
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public int getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(int cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

}
