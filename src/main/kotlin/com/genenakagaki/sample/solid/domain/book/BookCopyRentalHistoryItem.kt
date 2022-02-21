package com.genenakagaki.sample.solid.domain.book

import java.time.LocalDateTime

data class BookCopyRentalHistoryItem(
    var bookCopyId: Int,
    var rentedAt: LocalDateTime,
    var username: String,
    var returnDueAt: LocalDateTime,
    var returnedAt: LocalDateTime?
) {

}
