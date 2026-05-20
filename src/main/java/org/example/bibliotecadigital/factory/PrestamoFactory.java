package org.example.bibliotecadigital.factory;

import org.example.bibliotecadigital.model.Prestamo;
import java.time.LocalDate;

public class PrestamoFactory{

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

    public static Prestamo crearPrestamoConRenovacion(Prestamo prestamoOriginal){
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setIdLibro(prestamoOriginal.getIdLibro());
        nuevoPrestamo.setIdUsuario(prestamoOriginal.getIdUsuario());
        nuevoPrestamo.setFechaPrestamo(LocalDate.now());
        nuevoPrestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
        nuevoPrestamo.setFechaDevolucionReal(null);
        nuevoPrestamo.setEstado("ACTIVO");
        nuevoPrestamo.setMulta(0.0);
        return nuevoPrestamo;
    }
}