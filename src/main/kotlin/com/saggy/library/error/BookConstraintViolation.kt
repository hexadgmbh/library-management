package com.saggy.library.error

data class BookConstraintViolation(override val message: String) : RuntimeException(message)

data class BookAlreadyPresent(override val message: String) : RuntimeException(message)
