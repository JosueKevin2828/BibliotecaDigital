package org.example.bibliotecadigital.observer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PrestamoObserver implements Observador{

    @Override
    public void actualizar(String mensaje){
        mostrarAlerta(mensaje);
    }

    private void mostrarAlerta(String mensaje){
        // Muestra una alerta en pantalla
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Notificacion de Prestamos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}