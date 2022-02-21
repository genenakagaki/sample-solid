package com.genenakagaki.sample.solid.controller

import com.genenakagaki.sample.solid.service.BookRentalService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookRentalService: BookRentalService,
) {

    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody formModel: RentBookFormModel) {
        bookRentalService.rentBook(formModel.username, formModel.bookId)
    }

    @PostMapping("/api/book/return")
    fun returnBook(@RequestBody formModel: ReturnBookFormModel) {
        bookRentalService.returnBook(formModel.username, formModel.bookCopyId)
    }
}
