package org.example.bibliotecadigital.model;

public class UsuarioLogin{
    private int idLogin;
    private String username;
    private String passwordHash;
    private int idUsuario;

    public UsuarioLogin(){
    }

    public UsuarioLogin(int idLogin, String username, String passwordHash, int idUsuario){
        this.idLogin = idLogin;
        this.username = username;
        this.passwordHash = passwordHash;
        this.idUsuario = idUsuario;
    }

    public int getIdLogin(){
        return idLogin;
    }

    public void setIdLogin(int idLogin){
        this.idLogin = idLogin;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString(){
        return username;
    }
}