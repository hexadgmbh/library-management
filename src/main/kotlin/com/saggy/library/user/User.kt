package com.saggy.library.user

import com.saggy.library.error.BookAlreadyPresent
import com.saggy.library.error.BookConstraintViolation

data class User(val id: String, val name: String) {
    private val bookList = mutableListOf<String>()

    val getBooks = bookList

    fun addBook(bookId: String): Boolean {
        if (bookList.size == 2) {
            throw BookConstraintViolation("User can borrow at most 2 books at a time, please submit and then borrow")
        }
        if (bookList.contains(bookId)) {
            throw BookAlreadyPresent("Book $bookId is already borrowed")
        }
        return bookList.add(bookId)
    }

}