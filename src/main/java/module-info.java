module tea.craft {
    requires static lombok;
    requires javafx.graphics;
    requires MaterialFX;
    requires java.sql;
    requires java.sql.rowset;
    requires java.mail;
    requires jasperreports;
    requires webcam.capture;
    requires com.google.zxing;
    requires javafx.swing;
    requires com.google.zxing.javase;

    opens lk.ijse.teacraft to javafx.fxml;
    opens lk.ijse.teacraft.controller to javafx.fxml;
    exports lk.ijse.teacraft.controller;
    exports lk.ijse.teacraft;
}