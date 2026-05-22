package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.UsuarioDAO;
import org.example.bibliotecadigital.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UsuariosController{

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colTelefono;
    @FXML private TableColumn<Usuario, String> colTipo;

    private UsuarioDAO usuarioDAO;
    private ObservableList<Usuario> listaUsuarios;

    public UsuariosController(){
        usuarioDAO = new UsuarioDAO();
        listaUsuarios = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        cbTipo.getItems().addAll("ESTUDIANTE", "DOCENTE", "ADMIN");
        cbTipo.setValue("ESTUDIANTE");

        tablaUsuarios.setItems(listaUsuarios);

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if(selected != null){
                cargarUsuarioEnFormulario(selected);
            }
        });

        cargarTodosLosUsuarios();
    }

    private void cargarTodosLosUsuarios(){
        List<Usuario> usuarios = usuarioDAO.findAll();
        listaUsuarios.clear();
        listaUsuarios.addAll(usuarios);
    }

    private void cargarUsuarioEnFormulario(Usuario u){
        txtNombre.setText(u.getNombre());
        txtEmail.setText(u.getEmail());
        txtTelefono.setText(u.getTelefono());
        txtDireccion.setText(u.getDireccion());
        cbTipo.setValue(u.getTipo());
    }

    private void limpiarFormulario(){
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        cbTipo.setValue("ESTUDIANTE");
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleAgregar(){
        if(txtNombre.getText().isEmpty() || txtEmail.getText().isEmpty()){
            mostrarAlerta("Error", "Nombre y email son obligatorios");
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText());
        u.setEmail(txtEmail.getText());
        u.setTelefono(txtTelefono.getText());
        u.setDireccion(txtDireccion.getText());
        u.setTipo(cbTipo.getValue());
        u.setFechaRegistro(LocalDate.now());

        usuarioDAO.save(u);
        cargarTodosLosUsuarios();
        limpiarFormulario();
        mostrarAlerta("Exito", "Usuario agregado correctamente");
    }

    @FXML
    public void handleActualizar(){
        Usuario selected = tablaUsuarios.getSelectionModel().getSelectedItem();
        if(selected == null){
            mostrarAlerta("Error", "Seleccione un usuario");
            return;
        }

        selected.setNombre(txtNombre.getText());
        selected.setEmail(txtEmail.getText());
        selected.setTelefono(txtTelefono.getText());
        selected.setDireccion(txtDireccion.getText());
        selected.setTipo(cbTipo.getValue());

        usuarioDAO.update(selected);
        cargarTodosLosUsuarios();
        limpiarFormulario();
        mostrarAlerta("Exito", "Usuario actualizado");
    }

    @FXML
    public void handleEliminar(){
        Usuario selected = tablaUsuarios.getSelectionModel().getSelectedItem();
        if(selected == null){
            mostrarAlerta("Error", "Seleccione un usuario");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setContentText("Eliminar usuario: " + selected.getNombre() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            usuarioDAO.delete(selected.getIdUsuario());
            cargarTodosLosUsuarios();
            limpiarFormulario();
            mostrarAlerta("Exito", "Usuario eliminado");
        }
    }

    @FXML
    public void handleBuscar(){
        String busqueda = txtBuscar.getText().trim();
        if(busqueda.isEmpty()){
            cargarTodosLosUsuarios();
        }else{
            Optional<Usuario> u = usuarioDAO.buscarPorEmail(busqueda);
            listaUsuarios.clear();
            if(u.isPresent()){
                listaUsuarios.add(u.get());
            }else{
                mostrarAlerta("Info", "No encontrado");
            }
        }
    }

    @FXML
    public void handleMostrarTodos(){
        txtBuscar.clear();
        cargarTodosLosUsuarios();
    }

    @FXML
    public void handleVolver(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.cargarEstadisticas();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.getScene().setRoot(root);
        }catch(Exception e){
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