module org.example.chessgamejava {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.chessgamejava to javafx.fxml;
    exports org.example.chessgamejava;
}