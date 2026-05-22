package org.example.bibliotecadigital.controller;

import org.example.bibliotecadigital.dao.LibroDAO;
import org.example.bibliotecadigital.dao.PrestamoDAO;
import org.example.bibliotecadigital.dao.UsuarioDAO;
import org.example.bibliotecadigital.factory.PrestamoFactory;
import org.example.bibliotecadigital.model.Libro;
import org.example.bibliotecadigital.model.Prestamo;
import org.example.bibliotecadigital.model.Usuario;
import org.example.bibliotecadigital.observer.NotificadorMultas;
import org.example.bibliotecadigital.observer.PrestamoObserver;
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

public class PrestamosController{

    @FXML private ComboBox<Libro> cbLibro;
    @FXML private ComboBox<Usuario> cbUsuario;
    @FXML private DatePicker dpFechaPrestamo;
    @FXML private DatePicker dpFechaDevolucion;
    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, Integer> colId;
    @FXML private TableColumn<Prestamo, Integer> colIdLibro;
    @FXML private TableColumn<Prestamo, Integer> colIdUsuario;
    @FXML private TableColumn<Prestamo, LocalDate> colFechaPrestamo;
    @FXML private TableColumn<Prestamo, LocalDate> colFechaDevolucion;
    @FXML private TableColumn<Prestamo, String> colEstado;
    @FXML private TableColumn<Prestamo, Double> colMulta;

    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    private PrestamoDAO prestamoDAO;
    private ObservableList<Prestamo> listaPrestamos;
    private NotificadorMultas notificador;
    private PrestamoObserver observer;

    public PrestamosController(){
        libroDAO = new LibroDAO();
        usuarioDAO = new UsuarioDAO();
        prestamoDAO = new PrestamoDAO();
        listaPrestamos = FXCollections.observableArrayList();
        notificador = new NotificadorMultas();
        observer = new PrestamoObserver();
        notificador.agregarObservador(observer);
    }

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("idPrestamo"));
        colIdLibro.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucionEsperada"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colMulta.setCellValueFactory(new PropertyValueFactory<>("multa"));

        cargarLibros();
        cargarUsuarios();

        dpFechaPrestamo.setValue(LocalDate.now());
        dpFechaDevolucion.setValue(LocalDate.now().plusDays(7));

        tablaPrestamos.setItems(listaPrestamos);
        cargarTodosLosPrestamos();
        verificarPrestamosVencidos();
    }

    private void cargarLibros(){
        List<Libro> libros = libroDAO.findAll();
        cbLibro.setItems(FXCollections.observableArrayList(libros));
        cbLibro.setCellFactory(lv -> new ListCell<Libro>(){
            @Override
            protected void updateItem(Libro item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitulo());
            }
        });
    }

    private void cargarUsuarios(){
        List<Usuario> usuarios = usuarioDAO.findAll();
        cbUsuario.setItems(FXCollections.observableArrayList(usuarios));
        cbUsuario.setCellFactory(lv -> new ListCell<Usuario>(){
            @Override
            protected void updateItem(Usuario item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
    }

    private void cargarTodosLosPrestamos(){
        List<Prestamo> prestamos = prestamoDAO.findAll();
        listaPrestamos.clear();
        listaPrestamos.addAll(prestamos);
    }

    private void verificarPrestamosVencidos(){
        List<Prestamo> prestamos = prestamoDAO.findAll();
        boolean hayVencidos = false;

        for(Prestamo p : prestamos){
            if(p.getEstado().equals("ACTIVO") && LocalDate.now().isAfter(p.getFechaDevolucionEsperada())){
                hayVencidos = true;
                Optional<Usuario> usuario = usuarioDAO.findById(p.getIdUsuario());
                Optional<Libro> libro = libroDAO.findById(p.getIdLibro());
                long dias = java.time.temporal.ChronoUnit.DAYS.between(p.getFechaDevolucionEsperada(), LocalDate.now());

                if(usuario.isPresent() && libro.isPresent()){
                    notificador.notificarPrestamoRetrasado(usuario.get().getNombre(), libro.get().getTitulo(), (int)dias);
                }
            }
        }

        if(hayVencidos){
            notificador.verificarPrestamoVencido();
        }
    }

    @FXML
    public void handleRegistrarPrestamo(){
        Libro libro = cbLibro.getValue();
        Usuario usuario = cbUsuario.getValue();

        if(libro == null || usuario == null){
            mostrarAlerta("Error", "Seleccione libro y usuario");
            return;
        }

        if(libro.getCantidadDisponible() <= 0){
            mostrarAlerta("Error", "No hay ejemplares disponibles");
            return;
        }

        Prestamo prestamo = PrestamoFactory.crearPrestamo(libro.getIdLibro(), usuario.getIdUsuario());
        prestamoDAO.save(prestamo);

        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        libroDAO.update(libro);

        cargarLibros();
        cargarTodosLosPrestamos();
        mostrarAlerta("Exito", "Prestamo registrado");
        verificarPrestamosVencidos();
    }

    @FXML
    public void handleRegistrarDevolucion(){
        Prestamo selected = tablaPrestamos.getSelectionModel().getSelectedItem();
        if(selected == null){
            mostrarAlerta("Error", "Seleccione un prestamo");
            return;
        }

        if(selected.getEstado().equals("DEVUELTO")){
            mostrarAlerta("Error", "Ya fue devuelto");
            return;
        }

        selected.setFechaDevolucionReal(LocalDate.now());
        selected.setEstado("DEVUELTO");

        if(LocalDate.now().isAfter(selected.getFechaDevolucionEsperada())){
            long dias = java.time.temporal.ChronoUnit.DAYS.between(selected.getFechaDevolucionEsperada(), LocalDate.now());
            selected.setMulta(dias * 5);
            mostrarAlerta("Multa", "Multa aplicada: $" + (dias * 5));
        }

        prestamoDAO.update(selected);

        Optional<Libro> libro = libroDAO.findById(selected.getIdLibro());
        if(libro.isPresent()){
            libro.get().setCantidadDisponible(libro.get().getCantidadDisponible() + 1);
            libroDAO.update(libro.get());
        }

        cargarLibros();
        cargarTodosLosPrestamos();
        mostrarAlerta("Exito", "Devolucion registrada");
        verificarPrestamosVencidos();
    }

    @FXML
    public void handleVolver(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.cargarEstadisticas();
            Stage stage = (Stage) cbLibro.getScene().getWindow();
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