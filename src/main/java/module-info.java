module org.example.bibliotecadigital{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.bibliotecadigital to javafx.fxml;
    opens org.example.bibliotecadigital.controller to javafx.fxml;
    opens org.example.bibliotecadigital.model to javafx.base;
    exports org.example.bibliotecadigital;
    exports org.example.bibliotecadigital.controller;
    exports org.example.bibliotecadigital.model;
}