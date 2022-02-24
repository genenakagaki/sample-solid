package com.genenakagaki.sample.solid.controller

import com.genenakagaki.sample.solid.service.BookRentalService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class BookRentalController(
    private val bookRentalService: BookRentalService,
) {
    /**
     * bodyのサンプルデータ
     * {
     *   username: "g-nakagaki",
     *   book_id: "1"
     * }
     */
    @GetMapping("/api/book/view")
    fun viewBook(@RequestBody body: Map<String, String>): Mono<Any?> {
        val bookContent = bookRentalService.viewBook(body["username"], body["book_id"])
        return Mono.just(bookContent)
    }

    /**
     * bodyのサンプルデータ
     * {
     *   username: "g-nakagaki",
     *   book_id: "1"
     * }
     */
    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody body: Map<String, String>) {
        bookRentalService.rentBook(body["username"], body["book_id"])
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: RuntimeException): Mono<String> {
        return Mono.just(e.message ?: "")
    }
}
