package com.genenakagaki.sample.solid

/**
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
class UserBookRentalModel(

) {
    private fun isBookRented(bookId: Int): Boolean {
        for (rental in currentRentalList) {
            if (rental.bookId == bookId) {
                return true
            }
        }

        return false
    }

    fun rentBook(bookId: Int, bookPrice: BookPrice) {
        if (isBookRented(bookId)) {
            throw CustomException("既に借りてます。")
        }

        if (userCredit < bookPrice.rentPrice) {
            throw CustomException("お金が足りません。")
        }

        userCredit -= bookPrice.rentPrice
        val entry = UserBookRentalEntry.create(bookId)
        currentRentalList.add(entry)
        rentalHistory.add(entry)
    }

    fun applyRentalPeriod() {
        currentRentalList = currentRentalList.filter { r -> !r.isRentalPeriodFinished() }.toMutableList()
    }
}