package lms.lib;


public class Book {

    private int id;
    private String title;
    private String author;
    private boolean is_Borrowed;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.is_Borrowed = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public boolean isBorrowed() { return is_Borrowed; }
    public void setBorrowed(boolean is_Borrowed) { this.is_Borrowed = is_Borrowed; }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isBorrowed=" + is_Borrowed +
               '}';
    }
}



