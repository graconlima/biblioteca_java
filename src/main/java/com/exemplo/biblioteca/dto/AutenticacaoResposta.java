package com.exemplo.biblioteca.dto;

public class AutenticacaoResposta {
    private String token;

    public AutenticacaoResposta(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
