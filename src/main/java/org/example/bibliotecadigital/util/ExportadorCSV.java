package org.example.bibliotecadigital.util;

import org.example.bibliotecadigital.model.Libro;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV{

    public static void exportarLibros(List<Libro> libros, String rutaArchivo) throws IOException{
        try(FileWriter writer = new FileWriter(rutaArchivo)){
            writer.write("ID,Titulo,Autor,Categoria,ISBN,Cantidad Disponible\n");

            for(Libro libro : libros){
                writer.write(libro.getIdLibro() + ",");
                writer.write(escaparCSV(libro.getTitulo()) + ",");
                writer.write(escaparCSV(libro.getAutor()) + ",");
                writer.write(escaparCSV(libro.getCategoria()) + ",");
                writer.write(escaparCSV(libro.getIsbn()) + ",");
                writer.write(libro.getCantidadDisponible() + "\n");
            }
        }
    }

    private static String escaparCSV(String valor){
        if(valor == null) return "";
        if(valor.contains(",") || valor.contains("\"")){
            valor = valor.replace("\"", "\"\"");
            return "\"" + valor + "\"";
        }
        return valor;
    }
}