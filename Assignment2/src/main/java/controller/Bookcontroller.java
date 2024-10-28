package controller;

import dao.bookdb;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Book;
import model.Model;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bookcontroller {
    @FXML
    private TableView<Book> table1;


    @FXML
    private TableColumn<Book, String> allbook1;

    @FXML
    private TableColumn<Book, String> author1;

    @FXML
    private TableColumn<Book, Double> price1;

    @FXML
    private TableColumn<Book, Void> actionColumn1;

    @FXML
    private Label welcome1;

    @FXML
    private Label message1;

    Model model= new Model();
    @FXML
    public void initialize() throws SQLException {
        message1.setText("Welcome");
        bookdb books = new bookdb();
        books.load();
        ArrayList<Book> alldata = new ArrayList<>();
        ArrayList<Book> allbook = books.getbooks();
        ArrayList<Book> Author= books.getAllauthour();
        ArrayList<Book> allprices = books.getAllprices();
        for(int i=0;i<allbook.size();i++){
            Book b= new Book(allbook.get(i).getBook(),Author.get(i).getAuthor(),allprices.get(i).getPrice());
            alldata.add(b);
        }

        allbook1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
        author1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        price1.setCellValueFactory(cellData -> new  SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        actionColumn1.setCellFactory(param -> new TableCell<>() {
            private final Button addButton1 = new Button("Add");

            {
                addButton1.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    message1.setText(book.getBook()+"Book has been added to cart");
                    addToCart(book.getBook());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addButton1);
                }
            }
        });

        System.out.println(alldata);
        table1.getItems().setAll(alldata);
    }
    public void addToCart(String books){
        Book book = new Book();
        book.setBook(books);
        bookdb cartDB = new bookdb();  // Ensure `cartDB` references the same Cart
        cartDB.addtocart(book.getBook());
    }
}
