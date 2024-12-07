package model;

import java.time.LocalDate;

public class Funcionario {
    private int idFuncionario;
    private String nomeFuncionario;
    private String senha;
    private String cpfFuncionario;
    private LocalDate dtNascimento;
    private String emailFuncionario;

    public Funcionario(int idFuncionario, String nomeFuncionario, String senha, String cpfFuncionario, LocalDate dtNascimento, String emailFuncionario) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.senha = senha;
        this.cpfFuncionario = cpfFuncionario;
        this.dtNascimento = dtNascimento;
        this.emailFuncionario = emailFuncionario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

}

 