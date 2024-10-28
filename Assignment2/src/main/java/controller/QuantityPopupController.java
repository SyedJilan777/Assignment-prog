package controller;

import dao.bookdb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;

import java.util.HashMap;

public class QuantityPopupController {
    @FXML
    private TextField quantityField;

    @FXML
    private Button confirmButton;

    private String book;
    private HashMap<String, Integer> cart;
    private cartcontroller cartController;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> updateQuantity());
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setCart(HashMap<String, Integer> cart) {
        this.cart = cart;
        quantityField.setText(String.valueOf(cart.getOrDefault(book, 1)));
    }

    public void setCartController(cartcontroller cartController) {
        this.cartController = cartController;
    }

    private void updateQuantity() {
        try {
            int newQuantity = Integer.parseInt(quantityField.getText());
            if (newQuantity > 0) {
                cart.put(book, newQuantity);

                // Persist cart changes
                new bookdb().updateCart(cart);

                // Update the total in the cart controller

            }else{
                cart.remove(book);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity");
        }
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
}
