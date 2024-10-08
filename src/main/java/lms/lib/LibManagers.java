package lms.lib;

public interface LibManagers {

    void addBook(Book book);

    void updateBook(int id, String title, String author);

    void deleteBook(int id);

    void listBooks();

    void searchBooks(String query);

    void checkOutBook(int id);

    void checkInBook(int id);

    // Method to start the library management system
    void start();

}