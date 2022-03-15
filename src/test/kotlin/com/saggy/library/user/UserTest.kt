package com.saggy.library.user

import com.saggy.library.error.BookAlreadyPresent
import com.saggy.library.error.BookConstraintViolation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class UserTest {

    @Test
    fun `addBook- happy path`() {
        // given
        val user = User("user-1", "Sagar")

        // when
        val result = user.addBook("book-1")

        // then
        assertTrue(result)
    }

    @Test
    fun `addBook- should throw an BookConstraintViolation exception when adding third book`() {
        // given
        val user = User("user-1", "Sagar")
        user.addBook("book-1")
        user.addBook("book-2")

        // when
        val result = assertFailsWith<BookConstraintViolation> { user.addBook("book-3") }

        // then
        assertEquals("User can borrow at most 2 books at a time, please submit and then borrow", result.message)
    }

    @Test
    fun `addBook- should throw an BookAlreadyPresent exception when adding book which is already present`() {
        // given
        val user = User("user-1", "Sagar")
        user.addBook("book-1")


        // when
        val result = assertFailsWith<BookAlreadyPresent> { user.addBook("book-1") }

        // then
        assertEquals("Book book-1 is already borrowed", result.message)
    }
}