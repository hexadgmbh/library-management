package com.saggy.library.user

import com.saggy.library.error.UserNotFoundException

class UserService {

    private val users = mutableMapOf<String, User>()

    fun addBook(userId: String, bookId: String): Boolean {
        val user = get(userId)
        return user.addBook(bookId)
    }

    fun get(userId: String): User {
        if (users.containsKey(userId)) {
            return users[userId]!!
        }
        throw UserNotFoundException("user `$userId` not present")
    }

    fun addUser(user: User) {
        users[user.id] = user
    }

}
