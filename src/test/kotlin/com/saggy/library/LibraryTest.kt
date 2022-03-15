package com.saggy.library

import com.saggy.library.book.Book
import com.saggy.library.book.BookService
import com.saggy.library.user.UserService
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LibraryTest {

    @Mock
    private lateinit var bookService: BookService

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var subject: Library

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getAllBooks should return empty list when library dont have any book`() {
        // given
        given(bookService.getBooks()).willReturn(emptyList())

        // when
        val result = subject.getAllBooks()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllBooks should return 1 element list when one book is available`() {
        // given
        given(bookService.getBooks()).willReturn(
            listOf(
                Book(
                    "1",
                    "Harry potter",
                    "J L Rowling",
                    12000.98
                )
            )
        )

        // when
        val result = subject.getAllBooks()

        // then
        assertEquals(1, result.size)
    }

    @Test
    fun `borrowBook -- happy path`() {
        // given
        val userId = "user-1"
        val bookId = "book-1"
        val book = Book(bookId, "Harry Potter", "J K Rolling", 123.4)

        given(bookService.removeBook(anyString())).willReturn(book)
        given(userService.addBook(anyString(), anyString())).willReturn(true)

        // when
        val result = subject.borrowBook(userId, bookId)

        // then
        verify(bookService).removeBook(bookId)
        verify(userService).addBook(userId, bookId)
        assertTrue(result)
    }

    @Test
    fun `borrowBook -- should not add book to user when given book is not available in library`() {
        // given
        val userId = "user-1"
        val bookId = "book-1"
        val errMsg = "Book book-1 is not present in library"
        given(bookService.removeBook(bookId)).willThrow(RuntimeException(errMsg))

        // when
        val result = assertFailsWith<RuntimeException> { subject.borrowBook(userId, bookId) }

        // then
        assertEquals(errMsg, result.message)
        verify(bookService).removeBook(bookId)
        verifyNoInteractions(userService)
    }

    @Test
    fun `borrowBook -- should again add the book to library which was removed in request when user is unable to borrow book`() {
        // given
        val userId = "user-1"
        val book = Book("book-3", "Harry Potter", "J K Rolling", 123.4)
        given(bookService.removeBook(anyString())).willReturn(book)
        given(userService.addBook(userId, book.id)).willReturn(false)

        // when
        val result = subject.borrowBook(userId, book.id)

        // then
        verify(bookService).removeBook(book.id)
        verify(userService).addBook(userId, book.id)
        verify(bookService).addBook(book)
        assertFalse(result)
    }

}