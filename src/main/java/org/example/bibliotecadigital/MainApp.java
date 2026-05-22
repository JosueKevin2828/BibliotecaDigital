package org.example.bibliotecadigital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecadigital/view/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 400, 450);
            primaryStage.setTitle("Biblioteca Digital - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}Kevin123