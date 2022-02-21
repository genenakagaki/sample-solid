package com.genenakagaki.sample.solid.domain.book

import com.genenakagaki.sample.solid.CustomException
import java.time.LocalDateTime

class BookCopyRentalEntity(
    var bookCopyId: Int,
    var state: BookCopyStateType,
    var rentalHistory: MutableList<BookCopyRentalHistoryItem>
) {
    val isAvailable: Boolean
        get() {
            return state == BookCopyStateType.AVAILABLE
        }

    val lastRentalHistoryItem: BookCopyRentalHistoryItem?
        get() {
            return rentalHistory.maxByOrNull { h -> h.rentedAt }
        }

    fun rentBook(username: String) {
        state = BookCopyStateType.RENTED
        rentalHistory.add(
            BookCopyRentalHistoryItem(
                bookCopyId,
                LocalDateTime.now(),
                username,
                LocalDateTime.now().plusDays(7),
                null
            )
        )
    }

    fun returnBook(username: String) {
        val lastRentalHistoryItem = this.lastRentalHistoryItem
        if (lastRentalHistoryItem?.returnedAt == null) {
            throw CustomException("この本は借りられていません。")
        }

        state = BookCopyStateType.AVAILABLE
        lastRentalHistoryItem.returnedAt = LocalDateTime.now()
    }
}
