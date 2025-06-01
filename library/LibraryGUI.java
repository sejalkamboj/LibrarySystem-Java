import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryGUI {
    private JFrame frame;
    private LibrarySystem librarySystem;
    private JTable booksTable;
    private JComboBox<String> membersComboBox;
    private JComboBox<String> booksComboBox;

    public LibraryGUI() {
        librarySystem = new LibrarySystem();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Library Book Lending System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Books Tab
        JPanel booksPanel = new JPanel(new BorderLayout());
        booksTable = new JTable();
        updateBooksTable(librarySystem.getAllBooks());
        JScrollPane scrollPane = new JScrollPane(booksTable);
        booksPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel booksButtonPanel = new JPanel(new FlowLayout());
        JButton addBookButton = new JButton("Add New Book");
        JButton showAvailableButton = new JButton("Show Available Books");
        JButton showAllBooksButton = new JButton("Show All Books");
        
        booksButtonPanel.add(addBookButton);
        booksButtonPanel.add(showAvailableButton);
        booksButtonPanel.add(showAllBooksButton);
        booksPanel.add(booksButtonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Books", booksPanel);
        
        // Members Tab
        JPanel membersPanel = new JPanel(new BorderLayout());
        JTable membersTable = new JTable();
        updateMembersTable();
        JScrollPane membersScrollPane = new JScrollPane(membersTable);
        membersPanel.add(membersScrollPane, BorderLayout.CENTER);
        
        JPanel membersButtonPanel = new JPanel(new FlowLayout());
        JButton addMemberButton = new JButton("Add New Member");
        membersButtonPanel.add(addMemberButton);
        membersPanel.add(membersButtonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Members", membersPanel);
        
        // Lending Tab
        JPanel lendingPanel = new JPanel(new GridLayout(4, 1));
        
        // Issue Book Panel
        JPanel issuePanel = new JPanel(new FlowLayout());
        membersComboBox = new JComboBox<>();
        updateMembersComboBox();
        booksComboBox = new JComboBox<>();
        updateAvailableBooksComboBox();
        
        JButton issueButton = new JButton("Issue Book");
        issuePanel.add(new JLabel("Member:"));
        issuePanel.add(membersComboBox);
        issuePanel.add(new JLabel("Book:"));
        issuePanel.add(booksComboBox);
        issuePanel.add(issueButton);
        lendingPanel.add(issuePanel);
        
        // Return Book Panel
        JPanel returnPanel = new JPanel(new FlowLayout());
        JComboBox<String> returnMembersComboBox = new JComboBox<>();
        updateMembersComboBox();
        JComboBox<String> returnBooksComboBox = new JComboBox<>();
        
        JButton returnButton = new JButton("Return Book");
        returnPanel.add(new JLabel("Member:"));
        returnPanel.add(returnMembersComboBox);
        returnPanel.add(new JLabel("Book:"));
        returnPanel.add(returnBooksComboBox);
        returnPanel.add(returnButton);
        lendingPanel.add(returnPanel);
        
        tabbedPane.addTab("Lending", lendingPanel);
        
        frame.add(tabbedPane, BorderLayout.CENTER);
        
        // Add Book Button Action
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField idField = new JTextField();
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                
                Object[] message = {
                    "Book ID:", idField,
                    "Title:", titleField,
                    "Author:", authorField
                };
                
                int option = JOptionPane.showConfirmDialog(frame, message, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    String title = titleField.getText();
                    String author = authorField.getText();
                    
                    if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty()) {
                        Book book = new Book(id, title, author);
                        librarySystem.addBook(book);
                        updateBooksTable(librarySystem.getAllBooks());
                        updateAvailableBooksComboBox();
                        librarySystem.saveData();
                    } else {
                        JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Show Available Books Button Action
        showAvailableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooksTable(librarySystem.getAvailableBooks());
            }
        });
        
        // Show All Books Button Action
        showAllBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooksTable(librarySystem.getAllBooks());
            }
        });
        
        // Add Member Button Action
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                
                Object[] message = {
                    "Member ID:", idField,
                    "Name:", nameField
                };
                
                int option = JOptionPane.showConfirmDialog(frame, message, "Add New Member", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    String name = nameField.getText();
                    
                    if (!id.isEmpty() && !name.isEmpty()) {
                        Member member = new Member(id, name);
                        librarySystem.addMember(member);
                        updateMembersTable();
                        updateMembersComboBox();
                        librarySystem.saveData();
                    } else {
                        JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Issue Book Button Action
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberIndex = membersComboBox.getSelectedIndex();
                int bookIndex = booksComboBox.getSelectedIndex();
                
                if (memberIndex >= 0 && bookIndex >= 0) {
                    Member member = librarySystem.getAllMembers().get(memberIndex);
                    Book book = librarySystem.getAvailableBooks().get(bookIndex);
                    
                    try {
                        librarySystem.issueBook(member, book);
                        updateBooksTable(librarySystem.getAllBooks());
                        updateAvailableBooksComboBox();
                        librarySystem.saveData();
                        JOptionPane.showMessageDialog(frame, "Book issued successfully!");
                    } catch (MaxLimitReachedException | BookNotAvailableException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Return Book Button Action
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberIndex = returnMembersComboBox.getSelectedIndex();
                
                if (memberIndex >= 0) {
                    Member member = librarySystem.getAllMembers().get(memberIndex);
                    ArrayList<Book> borrowedBooks = member.getBorrowedBooks();
                    
                    if (borrowedBooks.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "This member has no borrowed books", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    // Update return books combo box with member's borrowed books
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                    for (Book book : borrowedBooks) {
                        model.addElement(book.getTitle() + " (" + book.getBookId() + ")");
                    }
                    returnBooksComboBox.setModel(model);
                    
                    int bookIndex = returnBooksComboBox.getSelectedIndex();
                    if (bookIndex >= 0) {
                        Book book = borrowedBooks.get(bookIndex);
                        
                        try {
                            librarySystem.returnBook(member, book);
                            updateBooksTable(librarySystem.getAllBooks());
                            updateAvailableBooksComboBox();
                            librarySystem.saveData();
                            JOptionPane.showMessageDialog(frame, "Book returned successfully!");
                        } catch (BookNotBorrowedException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        frame.setVisible(true);
    }

    private void updateBooksTable(ArrayList<Book> books) {
        String[] columnNames = {"Book ID", "Title", "Author", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable() ? "Available" : "Borrowed"
            };
            model.addRow(row);
        }
        
        booksTable.setModel(model);
    }

    private void updateMembersTable() {
        String[] columnNames = {"Member ID", "Name", "Borrowed Books"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Member member : librarySystem.getAllMembers()) {
            StringBuilder borrowedBooks = new StringBuilder();
            for (Book book : member.getBorrowedBooks()) {
                borrowedBooks.append(book.getTitle()).append(", ");
            }
            String borrowedStr = borrowedBooks.length() > 0 ? 
                borrowedBooks.substring(0, borrowedBooks.length() - 2) : "None";
            
            Object[] row = {
                member.getMemberId(),
                member.getName(),
                borrowedStr
            };
            model.addRow(row);
        }
    }

    private void updateMembersComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Member member : librarySystem.getAllMembers()) {
            model.addElement(member.getName() + " (" + member.getMemberId() + ")");
        }
        membersComboBox.setModel(model);
    }

    private void updateAvailableBooksComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Book book : librarySystem.getAvailableBooks()) {
            model.addElement(book.getTitle() + " (" + book.getBookId() + ")");
        }
        booksComboBox.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryGUI();
            }
        });
    }
}