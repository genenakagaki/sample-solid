package com.genenakagaki.sample.solid.domain.book

import com.genenakagaki.sample.solid.CustomException

class UserBookRentalModel(
    var username: String,
    var userCredit: Int,
    var currentRentalList: MutableList<UserBookRentalEntry>,
    var rentalHistory: MutableList<UserBookRentalEntry>
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
