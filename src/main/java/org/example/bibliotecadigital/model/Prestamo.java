package org.example.bibliotecadigital.model;

import java.time.LocalDate;

public class Prestamo{
    private int idPrestamo;
    private int idLibro;
    private int idUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private String estado; // "ACTIVO", "DEVUELTO", "VENCIDO"
    private double multa;

    public Prestamo(){
    }

    public Prestamo(int idPrestamo, int idLibro, int idUsuario, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal, String estado, double multa){
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.multa = multa;
    }

    public int getIdPrestamo(){
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo){
        this.idPrestamo = idPrestamo;
    }

    public int getIdLibro(){
        return idLibro;
    }

    public void setIdLibro(int idLibro){
        this.idLibro = idLibro;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaPrestamo(){
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo){
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada(){
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada){
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal(){
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal){
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public double getMulta(){
        return multa;
    }

    public void setMulta(double multa){
        this.multa = multa;
    }

    @Override
    public String toString(){
        return "Prestamo ID: " + idPrestamo + " - Estado: " + estado;
    }
}