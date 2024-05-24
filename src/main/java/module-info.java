module org.example.chessgamejava {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.chessgamejava to javafx.fxml;
    exports org.chessgamejava;
}