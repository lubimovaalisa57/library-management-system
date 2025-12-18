package library;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private OperationLog operationLog;

    // Конструктор
    public Library() {
        this.books = new ArrayList<>();
        this.operationLog = new OperationLog();
    }

    // Вложенный статический класс
    public static class OperationLog {
        // Внутренний класс для записи операции
        public class LogEntry {
            private OperationType type;
            private LocalDateTime timestamp;
            private String description;

            // конструктор
            public LogEntry(OperationType type, String description) {
                this.type = type;
                this.timestamp = LocalDateTime.now();
                this.description = description;
            }

            // геттеры
            public OperationType getType() { return type; }
            public LocalDateTime getTimestamp() { return timestamp; }
            public String getDescription() { return description; }

            // toString()
            @Override
            public String toString() {
                return String.format("[%s] %s - %s",
                        timestamp.toString().replace("T", " "),
                        type,
                        description);
            }
        }

        public enum OperationType {
            ADD_BOOK, BORROW, RETURN
        }

        private List<LogEntry> entries;

        // конструктор OperationLog
        public OperationLog() {
            this.entries = new ArrayList<>();
        }

        // методы
        public void addEntry(OperationType type, String description) {
            entries.add(new LogEntry(type, description));
        }

        public List<LogEntry> getEntries() {
            return new ArrayList<>(entries);
        }

        public void printLog() {
            System.out.println("=== Журнал операций ===");
            for (LogEntry entry : entries) {
                System.out.println(entry);
            }
            System.out.println("======================");
        }
    }

    // Методы библиотеки с записью в журнал
    public void addBook(Book book) {
        books.add(book);
        operationLog.addEntry(OperationLog.OperationType.ADD_BOOK,
                "Добавлена книга: " + book.getTitle());
    }

    public Book findBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public boolean borrowBook(int id) {
        Book book = findBookById(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            operationLog.addEntry(OperationLog.OperationType.BORROW,
                    "Выдана книга ID: " + id);
            return true;
        }
        return false;
    }

    public boolean returnBook(int id) {
        Book book = findBookById(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            operationLog.addEntry(OperationLog.OperationType.RETURN,
                    "Возвращена книга ID: " + id);
            return true;
        }
        return false;
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public void printOperationLog() {
        operationLog.printLog();
    }
    public String getStatistics() {
        long total = books.size();
        long available = books.stream().filter(Book::isAvailable).count();
        long borrowed = total - available;
        return String.format("Всего книг: %d, Доступно: %d, Выдано: %d",
                total, available, borrowed);
    }
    public boolean removeBook(int id) {
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (removed) {
            operationLog.addEntry(OperationLog.OperationType.ADD_BOOK,
                    "Удалена книга ID: " + id);
        }
        return removed;
    }
    public boolean updateBook(int id, Book newData) {
        Book book = findBookById(id);
        if (book != null) {
            // В реальном приложении нужно добавить сеттеры в Book
            // или создать новый объект
            operationLog.addEntry(OperationLog.OperationType.ADD_BOOK,
                    "Обновлена книга ID: " + id);
            return true;
        }
        return false;
    }
}