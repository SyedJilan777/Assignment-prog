package model;

public class Book {
    private String book;
    private String author;
    private int price;
    private int physical;
    private int sold;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book(){

    }
    public Book(String name, String author, int price) {
        this.book = name;
        this.author = author;
        this.price = price;
    }

    public Book(String book,String author,int price,int physical,int sold){
        this.book=book;
        this.author=author;
        this.physical=physical;
        this.sold=sold;
        this.price=price;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPhysical() {
        return physical;
    }

    public void setPhysical(int physical) {
        this.physical = physical;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
