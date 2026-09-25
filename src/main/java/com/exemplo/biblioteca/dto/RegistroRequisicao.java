package com.exemplo.biblioteca.dto;

import com.exemplo.biblioteca.model.Cargo;

public class RegistroRequisicao {
    private String login;
    private String senha;
    private Cargo cargo;

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }
}
