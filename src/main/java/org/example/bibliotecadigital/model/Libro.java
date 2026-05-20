package org.example.bibliotecadigital.model;

import java.time.LocalDate;

public class Libro{
    private int idLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private String isbn;
    private int cantidadDisponible;
    private LocalDate fechaRegistro;

    // Constructor vacio
    public Libro(){
    }

    // Constructor con parametros
    public Libro(int idLibro, String titulo, String autor, String categoria, String isbn, int cantidadDisponible, LocalDate fechaRegistro){
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.isbn = isbn;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getIdLibro(){
        return idLibro;
    }

    public void setIdLibro(int idLibro){
        this.idLibro = idLibro;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public int getCantidadDisponible(){
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible){
        this.cantidadDisponible = cantidadDisponible;
    }

    public LocalDate getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString(){
        return titulo + " - " + autor;
    }
}