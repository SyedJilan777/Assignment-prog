package controller;
import dao.Database;
import dao.bookdb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class checkoutcontroller {
    @FXML
    private TextField cnumber;

    @FXML
    private TextField month;

    @FXML
    private TextField year;

    @FXML
    private TextField cvv;

    @FXML
    private Button pay;

    @FXML
    private Label message;

    @FXML
    private void initialize(){
        pay.setOnAction(event -> paybutton());

    }
    public void paybutton(){
        if(cnumber.getText().length()==16  && Integer.parseInt(year.getText())>24 && cvv.getText().length()==3){
            message.setText("Payment Successful,Thank you for your purchase!");
            try {
                updatedatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            if(cnumber.getText().length()!=16){
                message.setText("Invalid Card Number");
            }
            if (Integer.parseInt(year.getText())<24){
                message.setText("Invalid Expire date");
            }
            if(cvv.getText().length()!=3){
                message.setText("Invalid CVV");
            }
        }
    }
    public void updatedatabase() throws SQLException {
        bookdb cart = new bookdb();
        HashMap<String,Integer> cart1=cart.getCart();
        HashMap<String, Integer> copies = cart.getPysical_copies();
        for(String r:cart1.keySet()) {
            String sql = "UPDATE Book SET physical_copies ="+(copies.get(r)-cart1.get(r))+" WHERE Book = "+r;
            try (Connection connection = Database.getConnection()) {
                Statement statement = connection.createStatement();
                try {
                    statement.executeQuery(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
