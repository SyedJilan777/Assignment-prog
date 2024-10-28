package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class bookdb {
    ArrayList<Book> allbook = new ArrayList<>();
    ArrayList<Book> allauthour = new ArrayList<>();
    ArrayList<Book> allprices = new ArrayList<>();
    HashMap<String, Integer> Price = new HashMap<>();
    HashMap<String, Integer> Pysical_copies = new HashMap<>();
    HashMap<String, Integer> Sold = new HashMap<>();
    // HashMap<Book, Integer> Cart = new HashMap<Book, Integer>();


    public ArrayList<Book> getAllauthour() {
        return allauthour;
    }

    public ArrayList<Book> getAllprices() {
        return allprices;
    }

    public void load() throws SQLException {
        String sql = "SELECT * FROM Book";
        try (Connection connection = Database.getConnection()) {
            Statement statement = connection.createStatement();
            try (ResultSet rs = statement.executeQuery(sql);) {
                while (rs.next()) {
                    Book book = new Book();
                    Book author = new Book();
                    Book price = new Book();

                    author.setAuthor(rs.getString("Author"));
                    book.setBook(rs.getString("Book"));
                    price.setPrice(rs.getInt("price"));
                    String b=rs.getString("Book");
                    int p=rs.getInt("price");
                    int phy=rs.getInt("physical_copies");
                    int so=rs.getInt("sold_copies");
                    allbook.add(book);
                    allauthour.add(author);
                    allprices.add(price);
                    Price.put(b,p);
                    Pysical_copies.put(b,phy);
                    Sold.put(b,so);

                    String hook = book.getBook();
                    System.out.println(hook);
                }
            }
        }
    }

    public ArrayList<Book> getbooks() {
        return allbook;
    }

    public HashMap<String, Integer> getPrice() {
        return Price;
    }

    public HashMap<String, Integer> getPysical_copies() {
        return Pysical_copies;
    }


    public HashMap<String, Integer> getSold() {
        return Sold;
    }

    private static final HashMap<String , Integer> Cart = new HashMap<>();

    public HashMap<String, Integer> getCart() {
        return Cart;
    }

    public void addtocart(String book) {
        if (Cart.containsKey(book)) {
            Cart.put(book, Cart.get(book) + 1);
        }else {
            Cart.put(book,1);
        }
        System.out.println("Added to cart: " + book + " - Quantity: " + Cart.get(book));
    }

    public void updatepassword(String username, String password) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection connection = Database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, username);
            try {
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password changed successfully!");
                } else {
                    System.out.println("User not found.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCart(HashMap<String, Integer> cart) {

    }
}
