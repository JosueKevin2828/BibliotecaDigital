package org.example.bibliotecadigital.observer;

// El sujeto, quien envía notificaciones
public interface Sujeto{
    void agregarObservador(Observador o);
    void eliminarObservador(Observador o);
    void notificarObservadores(String mensaje);
}