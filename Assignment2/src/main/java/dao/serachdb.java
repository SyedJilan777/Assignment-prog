package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;

public class serachdb {
    public ArrayList<Book> getAllbook() {
        return allbook;
    }

    public ArrayList<Book> getAllauthour() {
        return allauthour;
    }

    public ArrayList<Book> getAllprices() {
        return allprices;
    }

    ArrayList<Book> allbook = new ArrayList<>();
    ArrayList<Book> allauthour = new ArrayList<>();
    ArrayList<Book> allprices = new ArrayList<>();
    String keyword;


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void load() throws SQLException {
        String sql = "SELECT * FROM Book WHERE Book LIKE '%"+keyword+"%' OR Author LIKE '%"+keyword+"%'";
        try(Connection connection = Database.getConnection()){
            Statement statement = connection.createStatement();
            try (ResultSet rs = statement.executeQuery(sql);) {
                while (rs.next()) {
                    Book book = new Book();
                    Book author = new Book();
                    Book price = new Book();

                    author.setAuthor(rs.getString("Author"));
                    book.setBook(rs.getString("Book"));
                    price.setPrice(rs.getInt("price"));


                    allbook.add(book);
                    allauthour.add(author);
                    allprices.add(price);

                    String hook = book.getBook();
                    System.out.println(hook);
                }
            }
        }

    }
    public void clear(){
        allprices.clear();
        allbook.clear();
        allauthour.clear();;
    }
}
