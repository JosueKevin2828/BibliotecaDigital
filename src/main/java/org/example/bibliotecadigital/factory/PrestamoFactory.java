package org.example.bibliotecadigital.factory;

import org.example.bibliotecadigital.model.Prestamo;
import java.time.LocalDate;

public class PrestamoFactory{

    // Crea un prestamo con valores por defecto
    public static Prestamo crearPrestamo(int idLibro, int idUsuario){
        Prestamo prestamo = new Prestamo();
        prestamo.setIdLibro(idLibro);
        prestamo.setIdUsuario(idUsuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
        prestamo.setFechaDevolucionReal(null);
        prestamo.setEstado("ACTIVO");
        prestamo.setMulta(0.0);
        return prestamo;
    }

    // Aqui se irian agregando mas metodos para creacion de objetos en casos futuros, como Renovacion(no implemente)

}