package org.example.bibliotecadigital.model;

import java.time.LocalDate;

public class Usuario{
    private int idUsuario;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
    private String tipo; // "ESTUDIANTE", "DOCENTE", "ADMIN"

    public Usuario(){
    }

    public Usuario(int idUsuario, String nombre, String email, String telefono, String direccion, LocalDate fechaRegistro, String tipo){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.tipo = tipo;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public LocalDate getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        return nombre + " - " + email;
    }
}