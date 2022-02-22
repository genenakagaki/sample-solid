package com.genenakagaki.sample.solid

import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BookManagementControllerTest {

    @MockK
    lateinit var bookRepository: BookRepository

    @MockK
    lateinit var userRepository: UserRepository

    @InjectMockKs
    lateinit var SUT: BookManagementController

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun hello() {
        SUT.rentBook(mapOf(
            "username" to "g-nakagaki",
            "book_id" to "1",
        ))
    }
}
