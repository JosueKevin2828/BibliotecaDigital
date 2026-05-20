module org.example.bibliotecadigital {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bibliotecadigital to javafx.fxml;
    exports org.example.bibliotecadigital;
}