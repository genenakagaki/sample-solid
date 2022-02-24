package com.genenakagaki.sample.solid.controller

import com.genenakagaki.sample.solid.service.BookRentalService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class BookRentalController(
    private val bookRentalService: BookRentalService,
) {

    @GetMapping("/api/book/view")
    fun viewBook(@RequestBody body: ViewBookForm): Mono<Any> {
        val bookContent = bookRentalService.viewBook(body.username, body.bookId)
        return Mono.just(bookContent)
    }

    class ViewBookForm(
        val username: String,
        val bookId: Int,
    )

    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody body: RentBookForm) {
        bookRentalService.rentBook(body.username, body.bookId)
    }

    class RentBookForm(
        val username: String,
        val bookId: Int
    )

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: RuntimeException): Mono<String> {
        return Mono.just(e.message ?: "")
    }
}
