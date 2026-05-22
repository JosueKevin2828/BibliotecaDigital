package org.example.bibliotecadigital.util;

import org.example.bibliotecadigital.model.Libro;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorJSON{
    public static void exportarLibros(List<Libro> libros, String rutaArchivo) throws IOException{
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for(int i = 0; i < libros.size(); i++){
            Libro l = libros.get(i);
            json.append("  {\n");
            json.append("    \"id\": ").append(l.getIdLibro()).append(",\n");
            json.append("    \"titulo\": \"").append(escaparJSON(l.getTitulo())).append("\",\n");
            json.append("    \"autor\": \"").append(escaparJSON(l.getAutor())).append("\",\n");
            json.append("    \"categoria\": \"").append(escaparJSON(l.getCategoria())).append("\",\n");
            json.append("    \"isbn\": \"").append(escaparJSON(l.getIsbn())).append("\",\n");
            json.append("    \"cantidadDisponible\": ").append(l.getCantidadDisponible()).append("\n");
            json.append("  }");
            if(i < libros.size() - 1){
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");

        try(FileWriter writer = new FileWriter(rutaArchivo)){
            writer.write(json.toString());
        }
    }

    private static String escaparJSON(String valor){
        if(valor == null) return "";
        return valor.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }
}