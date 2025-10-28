package com.example.gui_optionalassignment1_doroudianishayan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
            Parent root = loader.load();

            // Apply default high-contrast theme class immediately (controller also sets it later)
            root.getStyleClass().add("theme-ocean-hc");

            // Scene with navy fill (matches CSS -app-bg)
            Scene scene = new Scene(root, 1280, 800);
            scene.setFill(Color.web("#081226"));

            // Attach app stylesheet
            String cssUrl = Objects.requireNonNull(
                    MainApplication.class.getResource("styles.css"),
                    "styles.css not found in resources path"
            ).toExternalForm();
            scene.getStylesheets().add(cssUrl);

            // Stage settings
            stage.setTitle("SwimDesk — GUI Optional Assignment");
            stage.setScene(scene);
            stage.setMinWidth(1100);
            stage.setMinHeight(720);
            stage.sizeToScene();

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load main.fxml", ex);
        }
    }
}
