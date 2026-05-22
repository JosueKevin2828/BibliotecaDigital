package org.example.bibliotecadigital.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificadorMultas implements Sujeto{
    private List<Observador> observadores;
    private String mensaje;

    public NotificadorMultas(){
        this.observadores = new ArrayList<>();
    }

    @Override
    public void agregarObservador(Observador o){
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(Observador o){
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores(String mensaje){
        this.mensaje = mensaje;
        for(Observador o : observadores){
            o.actualizar(mensaje);
        }
    }

    public void verificarPrestamoVencido(){
        this.mensaje = "ATENCION: Hay prestamos vencidos. Revise la lista de prestamos.";
        notificarObservadores(mensaje);
    }
}