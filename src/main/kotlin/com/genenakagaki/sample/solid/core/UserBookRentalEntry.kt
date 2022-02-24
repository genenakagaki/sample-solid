package com.genenakagaki.sample.solid.core

import java.time.LocalDateTime

class UserBookRentalEntry(
    val bookId: Int,
    val rentedAt: LocalDateTime,
    val rentUntil: LocalDateTime,
)

