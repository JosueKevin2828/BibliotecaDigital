package org.example.bibliotecadigital.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD{
    private static ConexionBD instancia;
    private Connection conexion;
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "carmelito123$";

    private ConexionBD(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static ConexionBD getInstance(){
        if(instancia == null){
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection(){
        return conexion;
    }
}