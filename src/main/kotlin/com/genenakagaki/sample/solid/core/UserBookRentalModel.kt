package com.genenakagaki.sample.solid.core

import com.genenakagaki.sample.solid.add
import com.genenakagaki.sample.solid.int
import com.genenakagaki.sample.solid.list
import java.time.LocalDateTime

class UserBookRentalModel {

    /**
     * userBookRentalData
     * {
     *   username: "g-nakagaki",
     *   userCredit: 500,
     *   currentRentalList: [
     *     {
     *       bookId: "1",
     *       rentedAt: "2022-01-01",
     *       rentUntil: "2022-01-08"
     *     }
     *   ],
     *   rentalHistory: [
     *     {
     *       bookId: "1",
     *       rentedAt: "2022-01-01",
     *       rentUntil: "2022-01-08"
     *     }
     *   ]
     * }
     */
    fun rentBook(bookId: Any?, bookRentalPrice: Any?, userBookRentalData: MutableMap<String, Any?>) {
        if (isBookRented(bookId, userBookRentalData)) {
            throw RuntimeException("既に借りてます。")
        }

        if (userBookRentalData["userCredit"].int() < bookRentalPrice.int()) {
            throw RuntimeException("お金が足りません。")
        }

        userBookRentalData["userCredit"] = userBookRentalData["userCredit"].int() - bookRentalPrice.int();

        val rentedAt = LocalDateTime.now()
        val rentUntil = rentedAt.plusDays(7)

        val entry = mutableMapOf(
            "bookId" to bookId,
            "rentedAt" to rentedAt,
            "rentUntil" to rentUntil,
        )
        userBookRentalData["currentRentalList"].add(entry)
        userBookRentalData["rentalHistory"].add(entry)
    }

    fun isBookRented(bookId: Any?, userBookRentalData: MutableMap<String, Any?>): Boolean {
        for (rental in userBookRentalData["currentRentalList"].list()) {
            if (rental["bookId"] == bookId) {
                return true
            }
        }

        return false
    }
}
