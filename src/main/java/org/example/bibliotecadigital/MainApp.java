package org.example.bibliotecadigital;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage){
        Label label = new Label("Sistema de Biblioteca Digital");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Biblioteca Digital");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}