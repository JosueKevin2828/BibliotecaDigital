package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.LibroDAO;
import org.example.bibliotecadigital.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bibliotecadigital.util.ExportadorCSV;
import org.example.bibliotecadigital.util.ExportadorJSON;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibrosController{

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Integer> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, String> colCategoria;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, Integer> colCantidad;

    private LibroDAO libroDAO;
    private ObservableList<Libro> listaLibros;

    public LibrosController(){
        libroDAO = new LibroDAO();
        listaLibros = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize(){
        // Configurar columnas - usar los nombres exactos de los getters
        colId.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));

        // Forzar que la tabla se actualice
        tablaLibros.setItems(listaLibros);
        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Listener para seleccion
        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if(selected != null){
                cargarLibroEnFormulario(selected);
            }
        });

        // Cargar libros
        cargarTodosLosLibros();
    }

    private void cargarTodosLosLibros(){
        List<Libro> libros = libroDAO.findAll();
        System.out.println("DEBUG: Libros encontrados: " + libros.size());
        listaLibros.clear();
        listaLibros.addAll(libros);
        tablaLibros.refresh();
        System.out.println("DEBUG: Tabla actualizada con " + listaLibros.size() + " libros");
    }

    private void cargarLibroEnFormulario(Libro libro){
        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtCategoria.setText(libro.getCategoria());
        txtIsbn.setText(libro.getIsbn());
        txtCantidad.setText(String.valueOf(libro.getCantidadDisponible()));
    }

    private void limpiarFormulario(){
        txtTitulo.clear();
        txtAutor.clear();
        txtCategoria.clear();
        txtIsbn.clear();
        txtCantidad.clear();
        tablaLibros.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleAgregar(){
        if(txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty()){
            mostrarAlerta("Error", "Titulo y autor son obligatorios");
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(txtTitulo.getText());
        libro.setAutor(txtAutor.getText());
        libro.setCategoria(txtCategoria.getText());
        libro.setIsbn(txtIsbn.getText());

        int cantidad = 1;
        try{
            cantidad = Integer.parseInt(txtCantidad.getText());
        }catch(NumberFormatException e){
            cantidad = 1;
        }
        libro.setCantidadDisponible(cantidad);
        libro.setFechaRegistro(LocalDate.now());

        libroDAO.save(libro);
        cargarTodosLosLibros();
        limpiarFormulario();
        mostrarAlerta("Exito", "Libro agregado correctamente");
    }

    @FXML
    public void handleActualizar(){
        Libro selected = tablaLibros.getSelectionModel().getSelectedItem();
        if(selected == null){
            mostrarAlerta("Error", "Seleccione un libro para actualizar");
            return;
        }

        selected.setTitulo(txtTitulo.getText());
        selected.setAutor(txtAutor.getText());
        selected.setCategoria(txtCategoria.getText());
        selected.setIsbn(txtIsbn.getText());

        int cantidad = 1;
        try{
            cantidad = Integer.parseInt(txtCantidad.getText());
        }catch(NumberFormatException e){
            cantidad = 1;
        }
        selected.setCantidadDisponible(cantidad);

        libroDAO.update(selected);
        cargarTodosLosLibros();
        limpiarFormulario();
        mostrarAlerta("Exito", "Libro actualizado correctamente");
    }

    @FXML
    public void handleEliminar(){
        Libro selected = tablaLibros.getSelectionModel().getSelectedItem();
        if(selected == null){
            mostrarAlerta("Error", "Seleccione un libro para eliminar");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setContentText("Eliminar libro: " + selected.getTitulo() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            libroDAO.delete(selected.getIdLibro());
            cargarTodosLosLibros();
            limpiarFormulario();
            mostrarAlerta("Exito", "Libro eliminado correctamente");
        }
    }

    @FXML
    public void handleBuscar(){
        String busqueda = txtBuscar.getText().trim();
        if(busqueda.isEmpty()){
            cargarTodosLosLibros();
        }else{
            List<Libro> libros = libroDAO.buscarPorTitulo(busqueda);
            listaLibros.clear();
            listaLibros.addAll(libros);
            tablaLibros.refresh();
        }
    }

    @FXML
    public void handleMostrarTodos(){
        txtBuscar.clear();
        cargarTodosLosLibros();
    }

    @FXML
    public void handleVolver(){
        try{
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) txtTitulo.getScene().getWindow();
            stage.getScene().setRoot(root);
        }catch(java.io.IOException e){
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void handleExportarCSV(){
        try{
            String nombreArchivo = "libros_exportados_" + System.currentTimeMillis() + ".csv";
            ExportadorCSV.exportarLibros(listaLibros, nombreArchivo);
            mostrarAlerta("Exito", "Exportado a CSV: " + nombreArchivo);
        }catch(Exception e){
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo exportar: " + e.getMessage());
        }
    }

    @FXML
    public void handleExportarJSON(){
        try{
            String nombreArchivo = "libros_exportados_" + System.currentTimeMillis() + ".json";
            ExportadorJSON.exportarLibros(listaLibros, nombreArchivo);
            mostrarAlerta("Exito", "Exportado a JSON: " + nombreArchivo);
        }catch(Exception e){
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo exportar: " + e.getMessage());
        }
    }

    @FXML
    public void testAgregarLibroPrueba(){
        Libro libro = new Libro();
        libro.setIdLibro(999);
        libro.setTitulo("LIBRO DE PRUEBA VISUAL");
        libro.setAutor("AUTOR PRUEBA");
        libro.setCategoria("TEST");
        libro.setIsbn("123456");
        libro.setCantidadDisponible(1);
        libro.setFechaRegistro(LocalDate.now());

        listaLibros.add(libro);
        tablaLibros.refresh();
        System.out.println("DEBUG: Libro prueba agregado, total: " + listaLibros.size());
    }
}