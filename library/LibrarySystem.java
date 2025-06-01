import java.io.*;
import java.util.ArrayList;

public class LibrarySystem {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";

    public LibrarySystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        loadData();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public void issueBook(Member member, Book book) throws MaxLimitReachedException, BookNotAvailableException {
        member.borrowBook(book);
    }

    public void returnBook(Member member, Book book) throws BookNotBorrowedException {
        member.returnBook(book);
    }

    public void saveData() {
        try (ObjectOutputStream oosBooks = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE));
             ObjectOutputStream oosMembers = new ObjectOutputStream(new FileOutputStream(MEMBERS_FILE))) {
            
            oosBooks.writeObject(books);
            oosMembers.writeObject(members);
            
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream oisBooks = new ObjectInputStream(new FileInputStream(BOOKS_FILE));
             ObjectInputStream oisMembers = new ObjectInputStream(new FileInputStream(MEMBERS_FILE))) {
            
            books = (ArrayList<Book>) oisBooks.readObject();
            members = (ArrayList<Member>) oisMembers.readObject();
            
        } catch (FileNotFoundException e) {
            System.out.println("No existing data files found. Starting with empty library.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}