package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.UsuarioLoginDAO;
import org.example.bibliotecadigital.model.UsuarioLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class LoginController{

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private UsuarioLoginDAO loginDAO;

    public LoginController(){
        loginDAO = new UsuarioLoginDAO();
    }

    @FXML
    public void handleLogin(){
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            lblError.setText("Complete todos los campos");
            return;
        }

        Optional<UsuarioLogin> usuario = loginDAO.autenticar(username, password);

        if(usuario.isPresent()){
            abrirDashboard(usuario.get().getUsername());
        }else{
            lblError.setText("Usuario o contrasena incorrectos");
        }
    }

    @FXML
    public void handleRegistro(){
        // TODO: ventana de registro
        lblError.setText("Funcionalidad en desarrollo");
    }

    private void abrirDashboard(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUsuarioActual(username);

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Biblioteca Digital - Dashboard");
            stage.setMaximized(true);
        }catch(IOException e){
            e.printStackTrace();
            lblError.setText("Error al cargar el dashboard");
        }
    }
}