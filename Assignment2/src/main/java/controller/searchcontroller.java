package controller;

import dao.bookdb;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Book;
import dao.serachdb;

import java.sql.SQLException;
import java.util.ArrayList;

public class searchcontroller {
    @FXML
    private TextField searchtext;

    @FXML
    private Label message;

    @FXML
    private Button searchbutton;

    @FXML
    private TableView<Book> stable;

    @FXML
    private TableColumn<Book,String> sbook;

    @FXML
    private TableColumn<Book,String> sauthor;

    @FXML
    private TableColumn<Book,Double> sprice;

    @FXML
    private TableColumn<Book,Void> saction;

    ArrayList<Book> alldata=new ArrayList<>();

    @FXML
    public void initialize() throws SQLException{
        searchbutton.setOnAction(event -> {

            String txt = searchtext.getText();
            serachdb serachdb= new serachdb();
            serachdb.setKeyword(txt);
            serachdb.clear();
            stable.getItems().clear();
            try {
                serachdb.load();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Book> booklist=serachdb.getAllbook();
            ArrayList<Book> Authorlist=serachdb.getAllauthour();
            ArrayList<Book> pricelist=serachdb.getAllprices();
            System.out.println(pricelist);
            System.out.println(booklist);
            displaybook(booklist,Authorlist,pricelist);
        });
    }

    public void displaybook(ArrayList<Book> book,ArrayList<Book> author,ArrayList<Book> price){
        sbook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
        sauthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        sprice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        saction.setCellFactory(param -> new TableCell<>() {
            private final Button addButton1 = new Button("Add");

            {
                addButton1.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    message.setText(book.getBook()+"Book has been added to cart");
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
        if(book.isEmpty()){
            message.setText("Not Found");
        }
        for(int i=0;i<book.size();i++){
            Book b= new Book(book.get(i).getBook(),author.get(i).getAuthor(),price.get(i).getPrice());
            alldata.add(b);
        }

        stable.getItems().setAll(alldata);

    }
    public void addToCart(String books){
        Book book = new Book();
        book.setBook(books);
        bookdb cartDB = new bookdb();  // Ensure `cartDB` references the same Cart
        cartDB.addtocart(book.getBook());
    }
}
