package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BookServiceTest {
    private BookService bookService;
    private User testUser;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        testUser = new User("testuser", "password", "test@example.com");
    }

    @Nested
    class SearchBookTests {
        @Test
        void testSearchBookByTitle() {
            Book book = new Book("Java Programming", "John Doe", "Programming", 29.99);
            bookService.addBook(book);
            List<Book> result = bookService.searchBook("Java");
            assertEquals(1, result.size());
            assertEquals("Java Programming", result.get(0).getTitle());
        }

        @Test
        void testSearchBookByAuthor() {
            Book book = new Book("Python Basics", "Jane Smith", "Programming", 24.99);
            bookService.addBook(book);
            List<Book> result = bookService.searchBook("Smith");
            assertEquals(1, result.size());
            assertEquals("Jane Smith", result.get(0).getAuthor());
        }

        @Test
        void testSearchBookByGenre() {
            Book book = new Book("Clean Code", "Robert Martin", "Programming", 39.99);
            bookService.addBook(book);
            List<Book> result = bookService.searchBook("Programming");
            assertEquals(1, result.size());
            assertEquals("Programming", result.get(0).getGenre());
        }

        @Test
        void testSearchNonExistentBook() {
            List<Book> result = bookService.searchBook("Nonexistent");
            assertTrue(result.isEmpty());
        }

        @Test
        void testSearchWithEmptyString() {
            Book book = new Book("Design Patterns", "Gang of Four", "Programming", 49.99);
            bookService.addBook(book);
            List<Book> result = bookService.searchBook("");
            assertFalse(result.isEmpty());  // Changed from assertTrue to assertFalse
            assertEquals(1, result.size()); // Add this line to verify the number of books returned
        }
    }

    @Nested
    class PurchaseBookTests {
        @Test
        void testSuccessfulPurchase() {
            Book book = new Book("Effective Java", "Joshua Bloch", "Programming", 44.99);
            bookService.addBook(book);
            assertTrue(bookService.purchaseBook(testUser, book));
        }

        @Test
        void testPurchaseNonExistentBook() {
            Book nonExistentBook = new Book("Nonexistent", "Unknown", "Fiction", 9.99);
            assertFalse(bookService.purchaseBook(testUser, nonExistentBook));
        }

        @Test
        void testRepurchaseSameBook() {
            Book book = new Book("Head First Java", "Kathy Sierra", "Programming", 34.99);
            bookService.addBook(book);
            assertTrue(bookService.purchaseBook(testUser, book));
            assertTrue(bookService.purchaseBook(testUser, book));
        }
    }

    @Nested
    class AddBookReviewTests {
        @Test
        void testAddValidReview() {
            Book book = new Book("Clean Architecture", "Robert Martin", "Programming", 39.99);
            bookService.addBook(book);
            testUser.getPurchasedBooks().add(book);
            assertTrue(bookService.addBookReview(testUser, book, "Excellent read!"));
            assertEquals(1, book.getReviews().size());
            assertEquals("Excellent read!", book.getReviews().get(0));
        }

        @Test
        void testAddReviewForUnpurchasedBook() {
            Book book = new Book("Unpurchased Book", "Some Author", "Fiction", 19.99);
            bookService.addBook(book);
            assertFalse(bookService.addBookReview(testUser, book, "Great book!"));
            assertTrue(book.getReviews().isEmpty());
        }

        @Test
        void testAddEmptyReview() {
            Book book = new Book("Test Book", "Test Author", "Test Genre", 9.99);
            bookService.addBook(book);
            testUser.getPurchasedBooks().add(book);
            assertTrue(bookService.addBookReview(testUser, book, ""));
            assertEquals(1, book.getReviews().size());
            assertEquals("", book.getReviews().get(0));
        }
    }

    @Nested
    class AddBookTests {
        @Test
        void testAddNewBook() {
            Book book = new Book("New Book", "New Author", "New Genre", 29.99);
            assertTrue(bookService.addBook(book));
        }

        @Test
        void testAddDuplicateBook() {
            Book book = new Book("Duplicate Book", "Some Author", "Some Genre", 19.99);
            assertTrue(bookService.addBook(book));
            assertFalse(bookService.addBook(book));
        }
    }

    @Nested
    class RemoveBookTests {
        @Test
        void testRemoveExistingBook() {
            Book book = new Book("Remove Me", "Author", "Genre", 9.99);
            bookService.addBook(book);
            assertTrue(bookService.removeBook(book));
        }

        @Test
        void testRemoveNonExistentBook() {
            Book book = new Book("Non-existent", "Author", "Genre", 9.99);
            assertFalse(bookService.removeBook(book));
        }

        @Test
        void testRemoveBookTwice() {
            Book book = new Book("Remove Twice", "Author", "Genre", 9.99);
            bookService.addBook(book);
            assertTrue(bookService.removeBook(book));
            assertFalse(bookService.removeBook(book));
        }
    }
}