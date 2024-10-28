package controller;

import dao.bookdb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Model;

import java.util.Objects;

public class ProfileController {
    @FXML
    private TextField pass;

    @FXML
    private TextField con;

    @FXML
    private Button update;

    public Button getUpdateButton() {
        return update;
    }

    @FXML
    private Label wel;

    @FXML
    private Label updated_message;

    String name;
    String password;
    String Confirm_password;

    @FXML
    public void initialize() {
        // Initialize or set actions for update button here if needed
        update.setOnAction(event ->validate());
    }

    public void setUsername(String username) {
        this.name=username;
    }

    public void validate(){
        this.password=pass.getText();
        this.Confirm_password=con.getText();
        if(this.password.isEmpty()){
            updated_message.setText("Enter the Password");
            updated_message.setTextFill(Color.RED);
            return;
        }
        if (this.Confirm_password.isEmpty()){
            updated_message.setText("Confirm the password");
            updated_message.setTextFill(Color.RED);
            return;
        }
        if(Objects.equals(this.Confirm_password, this.password)){
            System.out.println("Hi");
            bookdb book=new bookdb();
            book.updatepassword(name,password);
            updated_message.setText("Password changed successfully!");
            updated_message.setTextFill(Color.GREEN);
        }else{
            updated_message.setText("Password and confirm password are not same");
            updated_message.setTextFill(Color.RED);
            return;
        }
    }
}
