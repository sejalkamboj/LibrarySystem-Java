# 📚 Library Management System (Java Swing)

This is a **Library Management System** built using Java and Java Swing. It provides a graphical interface for managing books, members, issuing and returning books. This project demonstrates Object-Oriented Programming (OOP), exception handling, file management, and GUI in Java.

---

## 🖥️ Features

- Add & view books and members
- Borrow and return books
- Track availability of books
- Custom exception handling:
  - `MaxLimitReachedException`
  - `BookNotAvailableException`
  - `BookNotBorrowedException`
- Persistent storage using `.txt` files (`books.txt`, `members.txt`)
- User-friendly GUI with Java Swing

---

## 📸 Screenshots

### 🔹 GUI Home

![Library GUI](library/images/Screenshot%20(785).png)

### 🔹 Lending a Book

![Lend Book](library/images/Screenshot%20(786).png)

### 🔹 Add new Book

![Add new Book](library/images/Screenshot%20(787).png)

---

## 🛠️ Tech Stack

- Java
- Java Swing (for GUI)
- File I/O for persistence
- OOP principles
- Custom Exception Handling

---

## 📂 Project Structure
```
📁 library/
├── Book.java
├── Member.java
├── LibrarySystem.java
├── LibraryGUI.java
├── BookNotAvailableException.java
├── BookNotBorrowedException.java
├── MaxLimitReachedException.java
├── books.txt
├── members.txt
└── images/
```

---

## 🚀 Getting Started

### Prerequisites

- Java installed on your system
- VS Code or any Java IDE
- Git (optional)

### Run Instructions

```
 git clone https://github.com/sejalkamboj/LibrarySystem-Java.git
 cd LibrarySystem-Java
 javac *.java
 java LibraryGUI
```

---

### File Persistence
file_persistence:
  - books.txt: stores book information
  - members.txt: stores member data

> Note: `.class` files are ignored via `.gitignore`. To manually clean, use:
> - Windows CMD: `del *.class`
> - Linux/macOS/WSL: `rm *.class`

---



## 🌟Acknowledgment
This project is part of my academic Java practice to learn OOP, GUI, and file handling.

---

## 🧾 License

This project is licensed under the [MIT License](LICENSE).
