package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.LibroDAO;
import org.example.bibliotecadigital.dao.UsuarioDAO;
import org.example.bibliotecadigital.dao.PrestamoDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController{

    @FXML private Label lblUsuario;
    @FXML private Label lblTotalLibros;
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblPrestamosActivos;

    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    private PrestamoDAO prestamoDAO;

    public DashboardController(){
        libroDAO = new LibroDAO();
        usuarioDAO = new UsuarioDAO();
        prestamoDAO = new PrestamoDAO();
    }

    public void setUsuarioActual(String username){
        lblUsuario.setText("Bienvenido, " + username);
        cargarEstadisticas();
    }

    private void cargarEstadisticas(){
        long totalLibros = libroDAO.count();
        long totalUsuarios = usuarioDAO.count();
        long totalPrestamos = prestamoDAO.count();

        lblTotalLibros.setText(String.valueOf(totalLibros));
        lblTotalUsuarios.setText(String.valueOf(totalUsuarios));
        lblPrestamosActivos.setText(String.valueOf(totalPrestamos));
    }

    @FXML
    public void handleDashboard(){
        cargarEstadisticas();
    }

    @FXML
    public void handleLibros(){
        cambiarVista("/org/example/bibliotecadigital/view/libros.fxml");
    }

    @FXML
    public void handleUsuarios(){
        cambiarVista("/org/example/bibliotecadigital/view/usuarios.fxml");
    }

    @FXML
    public void handlePrestamos(){
        cambiarVista("/org/example/bibliotecadigital/view/prestamos.fxml");
    }

    @FXML
    public void handleReportes(){
        System.out.println("Reportes - En desarrollo");
    }

    @FXML
    public void handleLogout(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 450));
            stage.setTitle("Biblioteca Digital - Login");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void cambiarVista(String fxml){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.getScene().setRoot(root);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}