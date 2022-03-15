package com.saggy.library.error

data class UserNotFoundException(private val msg: String) : Exception(msg)
