module org.example.bibliotecadigital{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.bibliotecadigital to javafx.fxml;
    exports org.example.bibliotecadigital;
}