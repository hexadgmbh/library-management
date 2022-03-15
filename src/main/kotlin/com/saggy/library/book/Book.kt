package com.saggy.library.book

data class Book(val id: String, val name: String, val author: String, val prize: Double, var count: Int = 1)
