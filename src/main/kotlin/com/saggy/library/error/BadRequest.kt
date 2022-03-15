package com.saggy.library.error

data class BadRequest(override val message: String) : RuntimeException(message)