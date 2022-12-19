package br.com.newgo.mercado.models;

import javax.validation.constraints.NotBlank;

public class AutenticacaoRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    public AutenticacaoRequest() {
    }

    public AutenticacaoRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
