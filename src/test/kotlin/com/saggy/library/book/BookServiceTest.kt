package com.saggy.library.book

import com.saggy.library.error.BadRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class BookServiceTest {

    private val subject = BookService()

    @Test
    fun `getBooks should return empty list when no books available`() {
        //given

        //when
        val result = subject.getBooks()

        //then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getBooks should return list of available books`() {
        //given
        val book = Book("1", "Harry potter", "J L Rowling", 12000.98)

        //when
        var result = subject.getBooks()

        //then
        assertTrue(result.isEmpty())

        // when
        subject.addBook(book)
        result = subject.getBooks()

        // then
        assertFalse(result.isEmpty())
        assertEquals(book, result[0])

    }

    @Test
    fun `removeBook - should return true and remove book from library for given book is available in library`() {
        // given
        val bookId = "book-1"
        val book1 = Book(bookId, "Harry potter", "J K Rowling", 12000.98)

        subject.addBook(book1)
        subject.addBook(
            Book(
                "book-2",
                "Alone on a Wide, Wide Sea",
                "Michael Morpurgo",
                543.98
            )
        )
        subject.addBook(
            Book(
                "book-3",
                "O Jerusalem!",
                "Larry Collins",
                200.8
            )
        )

        //when
        val result = subject.removeBook(bookId)

        // then
        assertEquals(book1, result)

        val availableBooks = subject.getBooks()
        assertTrue(availableBooks.size == 2)
        assertTrue(availableBooks.none { it.id == bookId })
    }


    @Test
    fun `removeBook - should throw exception when book is not available in library`() {
        // given
        val bookId = "book-1"
        subject.addBook(
            Book(
                "book-2",
                "Alone on a Wide, Wide Sea",
                "Michael Morpurgo",
                543.98
            )
        )
        subject.addBook(
            Book(
                "book-3",
                "O Jerusalem!",
                "Larry Collins",
                200.8
            )
        )

        //when
        val result = assertFailsWith<Exception> { subject.removeBook(bookId) }

        // then
        assertEquals("Book `book-1` is not present in library", result.message)
    }

    @Test
    fun `addBook - should able to add 10 copies of the same book at a time`() {
        // given
        val book = Book("book-1", "Harry potter", "J K Rowling", 12000.98, 10)

        //when
        subject.addBook(book)

        // then
        val books = subject.getBooks()
        assertEquals(1, books.size)
        assertEquals(book, books[0])
    }

    @Test
    fun `addBook - should add same book with 10 copies separately`() {
        // given
        val book = Book("book-1", "Harry potter", "J K Rowling", 12000.98)

        // when
        for (i in 0..9) {
            subject.addBook(book.copy())
        }

        //then
        val result = subject.getBooks()
        assertEquals(1, result.size)
        assertEquals(10, result[0].count)
        assertEquals(book.copy(count = 10), result[0])
    }

    @Test
    fun `addBook - should throw validation exception when tried to add book with negative or zero count`() {
        // given
        val book = Book("book-1", "Harry potter", "J K Rowling", 12000.98, -1)

        // when
        val result = assertFailsWith<BadRequest> { subject.addBook(book.copy()) }

        //then
        assertEquals("Count can not be zero or negative for book", result.message)
    }

    @Test
    internal fun `removeBook - multiple scenarios`() {
        // given
        val id = "book-2"
        val book = Book(
            id,
            "Alone on a Wide, Wide Sea",
            "Michael Morpurgo",
            543.98,
            10
        )
        subject.addBook(book)

        for (i in 0..8) {
            //when
            val result = subject.removeBook(id)

            // then
            assertEquals(book.copy(count = 0), result)
            assertEquals(book.copy(count = 9 - i), subject.getBooks()[0])
        }

        // when  -- last element
        subject.removeBook(id)

        // then
        assertTrue(subject.getBooks().isEmpty())

        // when -- library does not have that book
        val result = assertFailsWith<Exception> { subject.removeBook(id) }

        // then
        assertEquals("Book `book-2` is not present in library", result.message)
    }
}