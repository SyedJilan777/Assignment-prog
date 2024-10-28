package controller;

import dao.bookdb;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Book;
import model.Model;

import javax.swing.text.html.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private Hyperlink profile;

	@FXML
	private BorderPane border;

	@FXML
	private Hyperlink serach;

	@FXML
	private ImageView dash;

	@FXML
	private Hyperlink book;

	@FXML
	private AnchorPane content;

	@FXML
	private GridPane grid;


	@FXML
	private Hyperlink cart;

	@FXML
	private BorderPane rootBorderPane;

	@FXML
	private Hyperlink signout;

	@FXML
	private Label message;

	@FXML
	private Label welcome;

	@FXML
	private Label text;

	@FXML
	private Hyperlink search;

	@FXML
	private Hyperlink Dashboard;

	@FXML
	private Hyperlink history;

	@FXML
	private Button test;// // Corresponds to the Menu item "updateProfile" in HomeView.fxml

	@FXML
	private TableView<Book> table;



	@FXML
	private TableColumn<Book, String> allbook;

	@FXML
	private TableColumn<Book, String> author;

	@FXML
	private TableColumn<Book, Double> price;

	@FXML
	private TableColumn<Book, Void> actionColumn;






	public HomeController(Stage parentStage, Model model) throws SQLException {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	// Add your code to complete the functionality of the program

	@FXML
	public void initialize() throws SQLException, FileNotFoundException {
		loadbooks();



		book.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookView.fxml"));
				Bookcontroller bookcontroller = new Bookcontroller();
				loader.setController(bookcontroller);
				AnchorPane bookpane = loader.load();
				content.getChildren().clear();
				content.getChildren().add(bookpane);
			}catch (IOException e) {
				message.setText(e.getMessage());
				System.out.println(e.getMessage());
			}
        });
		profile.setOnAction(event -> {
			content.getChildren().clear();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Profile.fxml"));

				ProfileController profileController = new ProfileController();
				loader.setController(profileController);
				AnchorPane profilePane = loader.load();
				profileController.setUsername(model.getCurrentUser().getUsername());
				content.getChildren().add(profilePane);
			}catch (IOException e) {
				message.setText(e.getMessage());
				System.out.println(e.getMessage());
			}

		});
		search.setOnAction(event -> {
			content.getChildren().clear();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/searchview.fxml"));

				searchcontroller searchcontroller= new searchcontroller();
				loader.setController(searchcontroller);
				AnchorPane cartpane = loader.load();
				content.getChildren().add(cartpane);
			}catch (IOException e) {
				message.setText(e.getMessage());
				System.out.println(e.getMessage());
			}
		});

		cart.setOnAction(event -> {
			content.getChildren().clear();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cart.fxml"));

				cartcontroller cartcontroller= new cartcontroller();
				loader.setController(cartcontroller);
				AnchorPane cartpane = loader.load();
				content.getChildren().add(cartpane);
			}catch (IOException e) {
				message.setText(e.getMessage());
				System.out.println(e.getMessage());
			}
		});

		Dashboard.setOnAction(event -> {
			try {
				content.getChildren().clear();
				content.getChildren().add(table);// Clear current content
				loadbooks();  // Reload the books
			} catch (SQLException e) {
				message.setText(e.getMessage());
				System.out.println(e.getMessage());
			}
		});




	}


	public void showStage(BorderPane root) throws SQLException {
		Scene scene = new Scene(root, 730, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
	public void addToCart(String books){
		Book book = new Book();
		book.setBook(books);
		bookdb cartDB = new bookdb();  // Ensure `cartDB` references the same Cart
		cartDB.addtocart(book.getBook());
	}

	public void loadbooks() throws SQLException {
		welcome.setText("Welcome "+model.getCurrentUser().getUsername().toUpperCase());
		bookdb bookDatabase = new bookdb();
		bookDatabase.load();
		ArrayList<Book> alldata = new ArrayList<>();
		ArrayList<Book> books = bookDatabase.getbooks();
		ArrayList<Book> allauthor = bookDatabase.getAllauthour();
		ArrayList<Book> allprice = bookDatabase.getAllprices();
		for(int i=0;i<books.size();i++){
			Book n = new Book(books.get(i).getBook(),allauthor.get(i).getAuthor(),allprice.get(i).getPrice());
			alldata.add(n);
		}
		// Set up table columns
		allbook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
		author.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
		price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

		// Set up "Add" button in the action column
		actionColumn.setCellFactory(param -> new TableCell<>() {
			private final Button addButton = new Button("Add");

			{
				addButton.setOnAction(event -> {
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
					setGraphic(addButton);
				}
			}
		});

		// Load books into the table
		table.getItems().setAll(alldata);
	}
}
