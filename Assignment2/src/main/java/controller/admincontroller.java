package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Model;

import java.sql.SQLException;

public class admincontroller {

    private Model model;
    private Stage stage;
    private Stage parentStage;

    public admincontroller(Stage parentStage, Model model) {
        this.parentStage = parentStage;
        this.model = model;
        this.stage = new Stage();  // Create a new stage for admin view
    }

    @FXML
    private Label profile;

    @FXML
    private Label stocks;

    @FXML
    private Label add;

    @FXML
    private void initialize() {
        // Initialize any necessary data or bindings here
    }

    public void showStage(AnchorPane root) throws SQLException {
        Scene scene = new Scene(root, 730, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Admin Dashboard");  // Set a meaningful title
        stage.show();
    }
}
