package com.genenakagaki.sample.solid.core

import com.genenakagaki.sample.solid.add
import com.genenakagaki.sample.solid.int
import com.genenakagaki.sample.solid.list
import java.time.LocalDateTime

class UserBookRentalModel(
    var username: String,
    var userCredit: Int,
    var currentRentalList: MutableList<UserBookRentalEntry>,
    var rentalHistory: MutableList<UserBookRentalEntry>,
) {
    fun rentBook(bookId: Int, bookRentalPrice: Int) {
        if (isBookRented(bookId)) {
            throw RuntimeException("既に借りてます。")
        }

        if (userCredit < bookRentalPrice) {
            throw RuntimeException("お金が足りません。")
        }

        userCredit -= bookRentalPrice

        val rentedAt = LocalDateTime.now()
        val rentUntil = rentedAt.plusDays(7)

        val entry = UserBookRentalEntry(
            bookId,
            rentedAt,
            rentUntil,
        )
        currentRentalList.add(entry)
        rentalHistory.add(entry)
    }

    fun isBookRented(bookId: Int): Boolean {
        for (rental in currentRentalList) {
            if (rental.bookId == bookId) {
                return true
            }
        }

        return false
    }
}
