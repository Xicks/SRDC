package sdr.ufscar.dev.srdc.model;

/**
 * Created by Schick on 8/23/16.
 */

public class Usuario {

    private Integer idUsuario;
    private String username;
    private String email;
    private String senha;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
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

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
