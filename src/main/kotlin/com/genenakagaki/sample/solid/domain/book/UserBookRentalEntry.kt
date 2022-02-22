package com.genenakagaki.sample.solid.domain.book

import java.time.LocalDateTime

data class UserBookRentalEntry(
    var bookId: Int,
    var rentedAt: LocalDateTime,
    var rentUntil: LocalDateTime,
) {
    companion object {
        fun create(bookId: Int): UserBookRentalEntry {
            return UserBookRentalEntry(
                bookId = bookId,
                rentedAt = LocalDateTime.now(),
                rentUntil = LocalDateTime.now().plusDays(7),
            )
        }
    }

    fun isRentalPeriodFinished(): Boolean {
        return LocalDateTime.now().isAfter(rentUntil)
    }
}
