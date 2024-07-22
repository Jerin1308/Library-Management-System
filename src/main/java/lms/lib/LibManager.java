package lms.lib;

import java.sql.*;
import java.util.Scanner;

public class LibManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/libmanager";
    private static final String USER = "Jerin1308..";
    private static final String PASS = "@Jerin1308..";

    public void addBook(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO library_books (id, title, author, is_Borrowed) VALUES (?, ?, ?, ?)")) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isBorrowed());
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void updateBook(int id, String title, String author) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE library_books SET title = ?, author = ? WHERE id = ?")) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully!");
            } else {
                System.out.println("Book not found!");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void deleteBook(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM library_books WHERE id = ?")) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Book not found!");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void listBooks() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM library_books")) {

            if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No books available.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") + ", Author: "
                            + rs.getString("author") + ", Borrowed: " + rs.getBoolean("is_borrowed"));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void searchBooks(String query) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM library_books WHERE title LIKE ? OR author LIKE ?")) {

            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            ResultSet rs = stmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") + ", Author: "
                        + rs.getString("author") + ", Borrowed: " + rs.getBoolean("is_Borrowed"));
                found = true;
            }
            if (!found) {
                System.out.println("No books found matching the query.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void checkOutBook(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("UPDATE library_books SET is_Borrowed = ? WHERE id = ?")) {

            stmt.setBoolean(1, true);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book checked out successfully!");
            } else {
                System.out.println("Book not found or already checked out.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void checkInBook(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("UPDATE library_books SET is_Borrowed = ? WHERE id = ?")) {

            stmt.setBoolean(1, false);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book checked in successfully!");
            } else {
                System.out.println("Book not found or was not checked out.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // Method to input book details
    private Book inputBookDetails(Scanner scanner) {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        return new Book(id, title, author);
    }

    // Method to start the library management system
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List All Books");
            System.out.println("5. Search Books");
            System.out.println("6. Check Out Book");
            System.out.println("7. Check In Book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Fix for Scanner bug
            switch (choice) {
                case 1:
                    addBook(inputBookDetails(scanner));
                    break;
                case 2:
                    System.out.print("Enter Book ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Book book = inputBookDetails(scanner);
                    updateBook(id, book.getTitle(), book.getAuthor());
                    break;
                case 3:
                    System.out.print("Enter Book ID to delete: ");
                    id = scanner.nextInt();
                    deleteBook(id);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    System.out.print("Enter search query (title or author): ");
                    String query = scanner.nextLine();
                    searchBooks(query);
                    break;
                case 6:
                    System.out.print("Enter Book ID to check out: ");
                    id = scanner.nextInt();
                    checkOutBook(id);
                    break;
                case 7:
                    System.out.print("Enter Book ID to check in: ");
                    id = scanner.nextInt();
                    checkInBook(id);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 8.");
                    break;
            }
        }
    }
}