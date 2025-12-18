package library;

public class Main {
    public static void main(String[] args) {
        // Создаём библиотеку
        Library library = new Library();

        // Добавление книг
        library.addBook(new Book(1, "Война и мир",
                "Л.Н. Толстой", 1869, "978-5-17-090335-2"));

        library.addBook(new Book(2, "Преступление и наказание",
                "Ф.М. Достоевский", 1866, "978-5-17-090336-9"));

        library.addBook(new Book(3, "Анна Каренина",
                "Л.Н. Толстой", 1877, "978-5-17-090337-6"));

        System.out.println("=== Поиск книги по ID ===");
        Book book1 = library.findBookById(1);
        if (book1 != null) {
            System.out.println(book1);
        }

        System.out.println("\n=== Поиск книг по автору ===");
        for (Book book : library.findBooksByAuthor("Л.Н. Толстой")) {
            System.out.println(book.getTitle());
        }

        System.out.println("\n=== Выдача книги ===");
        if (library.borrowBook(1)) {
            System.out.println("Книга ID:1 выдана");
            System.out.println(library.findBookById(1));
        }

        System.out.println("\n=== Доступные книги ===");
        for (Book book : library.getAvailableBooks()) {
            System.out.println(book.getTitle() + " (ID:" + book.getId() + ")");
        }

        System.out.println("\n=== Возврат книги ===");
        if (library.returnBook(1)) {
            System.out.println("Книга ID:1 возвращена");
        }

        System.out.println("\n=== Журнал операций ===");
        library.printOperationLog();
    }
}