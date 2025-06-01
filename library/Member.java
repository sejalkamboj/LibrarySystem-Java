import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private String memberId;
    private String name;
    private ArrayList<Book> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) throws MaxLimitReachedException, BookNotAvailableException {
        if (borrowedBooks.size() >= 3) {
            throw new MaxLimitReachedException("Member has already borrowed maximum 3 books");
        }
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for borrowing");
        }
        borrowedBooks.add(book);
        book.setAvailable(false);
    }

    public void returnBook(Book book) throws BookNotBorrowedException {
        if (!borrowedBooks.contains(book)) {
            throw new BookNotBorrowedException("This book was not borrowed by this member");
        }
        borrowedBooks.remove(book);
        book.setAvailable(true);
    }
}