package org.example.bibliotecadigital.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil{

    public static String encriptarSHA1(String password){
        try{
            // Obtener instancia del algoritmo SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // Convierte contraseña a bytes y encripta
            byte[] bytes = md.digest(password.getBytes());
            // Convierte bytes a hexadecimal
            StringBuilder sb = new StringBuilder();
            for(byte b : bytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error al encriptar password", e);
        }
    }

    public static boolean verificarPassword(String password, String hash){
        return encriptarSHA1(password).equals(hash);
    }
}