package lk.ijse.teacraft;


import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainForm.fxml"));
        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

    }
}