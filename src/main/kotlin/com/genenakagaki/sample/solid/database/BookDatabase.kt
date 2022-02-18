package com.genenakagaki.sample.solid

import java.time.LocalDate
import java.time.LocalDateTime

object BookDatabase {
    val userTable = mutableListOf(
        hashMapOf(
            "username" to "g-nakagaki",
            "role" to "USER",
            "createdAt" to LocalDate.of(2022, 1, 1)
        ),
        hashMapOf(
            "username" to "k-nishizawa",
            "role" to "ADMIN",
            "createdAt" to LocalDate.of(2022, 1, 1)
        )
    )

    val bookTable = mutableListOf(
        hashMapOf(
            "bookId" to "2bbfada9-5ddf-4067-b914-77d49861cf76",
            "title" to "A Book",
            "state" to "AVAILABLE",
            "stateHistory" to mutableListOf(
                hashMapOf(
                    "stateHistoryId" to 1L,
                    "state" to "AVAILABLE",
                    "createdAt" to LocalDate.of(2022, 1, 1)
                ),
                hashMapOf(
                    "stateHistoryId" to 2L,
                    "state" to "RENTED",
                    "createdAt" to LocalDate.of(2022, 1, 10)
                ),
                hashMapOf(
                    "stateHistoryId" to 3L,
                    "state" to "AVAILABLE",
                    "createdAt" to LocalDate.of(2022, 1, 20)
                )
            ),
            "createdAt" to LocalDate.of(2022, 1, 1)
        ),
        hashMapOf(
            "bookId" to 2L,
            "title" to "A Book: 2nd Edition",
            "state" to "AVAILABLE",
            "stateHistory" to mutableListOf(
                hashMapOf(
                    "stateHistoryId" to 1L,
                    "state" to "AVAILABLE",
                    "createdAt" to LocalDate.of(2022, 2, 1)
                ),
                hashMapOf(
                    "stateHistoryId" to 2L,
                    "state" to "RENTED",
                    "createdAt" to LocalDate.of(2022, 2, 10)
                ),
            ),
            "createdAt" to LocalDate.of(2022, 2, 1)
        ),
        hashMapOf(
            "bookId" to 3L,
            "title" to "A Book: 2nd Edition",
            "state" to "AVAILABLE",
            "stateHistory" to mutableListOf(
                hashMapOf(
                    "stateHistoryId" to 1L,
                    "state" to "AVAILABLE",
                    "createdAt" to LocalDate.of(2022, 2, 1)
                ),
            ),
            "createdAt" to LocalDate.of(2022, 2, 1)
        ),
    )

    val bookRentTable = mutableListOf(
        hashMapOf(
            "rentId" to 1L,
            "bookId" to 1L,
            "userId" to "k-nishizawa",
            "rentedAt" to LocalDate.of(2022, 1, 10),
            "returnDue" to LocalDate.of(2022, 1, 20),
            "returnedAt" to LocalDate.of(2022, 1, 20)
        ),
        hashMapOf(
            "rentId" to 2L,
            "bookId" to 2L,
            "userId" to "g-nakagaki",
            "rentedAt" to LocalDate.of(2022, 2, 10),
            "returnDue" to LocalDate.of(2022, 2, 20),
            "returnedAt" to null
        )
    )

}
