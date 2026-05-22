package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.UsuarioDAO;
import org.example.bibliotecadigital.dao.UsuarioLoginDAO;
import org.example.bibliotecadigital.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class RegistroController{

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private ComboBox<String> cbTipo;
    @FXML private Label lblError;

    private UsuarioDAO usuarioDAO;
    private UsuarioLoginDAO loginDAO;

    public RegistroController(){
        usuarioDAO = new UsuarioDAO();
        loginDAO = new UsuarioLoginDAO();
    }

    @FXML
    public void initialize(){
        cbTipo.getItems().addAll("ESTUDIANTE", "DOCENTE");
        cbTipo.setValue("ESTUDIANTE");
    }

    @FXML
    public void handleRegistrar(){
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String confirm = txtConfirmPassword.getText().trim();
        String tipo = cbTipo.getValue();

        if(nombre.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()){
            lblError.setText("Complete todos los campos obligatorios");
            return;
        }

        if(!password.equals(confirm)){
            lblError.setText("Las contrasenas no coinciden");
            return;
        }

        if(password.length() < 4){
            lblError.setText("La contrasena debe tener al menos 4 caracteres");
            return;
        }

        if(loginDAO.existsByUsername(username)){
            lblError.setText("El nombre de usuario ya existe");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setTipo(tipo);
        usuario.setFechaRegistro(LocalDate.now());

        usuario = usuarioDAO.save(usuario);

        boolean registrado = loginDAO.registrar(username, password, usuario.getIdUsuario());

        if(registrado){
            mostrarAlerta("Exito", "Cuenta creada correctamente. Ahora puede iniciar sesion.");
            volverALogin();
        }else{
            lblError.setText("Error al crear la cuenta");
        }
    }

    @FXML
    public void handleVolver(){
        volverALogin();
    }

    private void volverALogin(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 450));
            stage.setTitle("Biblioteca Digital - Login");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}