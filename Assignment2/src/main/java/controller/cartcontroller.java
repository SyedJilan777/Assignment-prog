package controller;

import dao.bookdb;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cartcontroller {
    @FXML
    private TableView<Book> table;


    @FXML
    private TableColumn<Book, String> allbook;

    @FXML
    private Label message;

    @FXML
    private TableColumn<Book, Integer> Quantity;

    @FXML
    private TableColumn<Book, Double> price;

    @FXML
    private Label total;

    @FXML
    private Button checkout;
    @FXML
    private TableColumn<Book, Void> actionColumn;

    private int sum=0;

    @FXML
    public void initialize() throws SQLException {
        bookdb bookDatabase = new bookdb();
        bookDatabase.load();
        ArrayList<Book> alldata = new ArrayList<>();
        HashMap<String, Integer> cart = bookDatabase.getCart();
        HashMap<String, Integer> prices = bookDatabase.getPrice();
        allbook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
        Quantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("Change Quantity");
            {
                addButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    showQuantityPopup(book,cart);
                    System.out.println(book.getBook());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addButton);
                }
            }
        });
        checkout.setOnAction(event -> {
            showpaypop(sum);
        });

        for(String e:cart.keySet()){
            int value = cart.get(e);
            Book temp=new Book();
            int p=prices.get(e);
            int price=p*value;
            sum=sum+price;
            temp.setBook(e);
            temp.setQuantity(value);
            temp.setPrice(price);
            alldata.add(temp);
        }
        table.getItems().setAll(alldata);
        total.setText("$"+sum);


    }
    private void showQuantityPopup(Book book, HashMap<String, Integer> cart) {
        try {
            // Load quantity pop-up view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuantityPopup.fxml"));
            AnchorPane pane = loader.load();

            // Configure the pop-up stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Update Quantity");
            popupStage.setScene(new Scene(pane));

            // Access QuantityPopupController to set up the book and cart
            QuantityPopupController controller = loader.getController();
            controller.setBook(book.getBook());
            controller.setCart(cart);
            controller.setCartController(this);  // To refresh table after update

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showpaypop(int sum2) {
        try {
            // Load quantity pop-up view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/checkout.fxml"));
            AnchorPane pane = loader.load();

            // Access QuantityPopupController to set up the book and cart
            Stage popsupStage = new Stage();
            popsupStage.initModality(Modality.APPLICATION_MODAL);
            popsupStage.setTitle("Payment Gateway");
            popsupStage.setScene(new Scene(pane));

            // Access CheckoutController to set up the total amount
            checkoutcontroller checkoutController = loader.getController(); // To refresh table after update

            popsupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
