package com.saggy.library

import com.saggy.library.book.Book
import com.saggy.library.book.BookService
import com.saggy.library.user.UserService

class Library(private val bookService: BookService, private val userService: UserService) {

    fun addBook(userId: String, book: Book): Boolean {
        return false
    }

    fun getAllBooks(): List<Book> {
        return bookService.getBooks()
    }

    fun borrowBook(userId: String, bookId: String): Boolean {
        var isBorrowed = false
        var borrowBook = bookService.removeBook(bookId)
        if (borrowBook != null) {
            val added = userService.addBook(userId, bookId)
            if (!added) {
                bookService.addBook(borrowBook)
            }
            isBorrowed = added
        }
        return isBorrowed
    }
}