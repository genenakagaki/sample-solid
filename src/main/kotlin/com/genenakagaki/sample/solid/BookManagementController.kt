package com.genenakagaki.sample.solid

import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.repository.UserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class BookManagementController(
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
) {

    @PostMapping("/api/book/add")
    fun addBook(@RequestBody body: Map<String, String>) {
        val userData = userRepository.selectByUsername(body["username"]!!)
        if (userData["role"] != "ADMIN") {
            throw UnauthorizedException()
        }

        bookRepository.insert(body["title"]!!)
    }

    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody body: Map<String, String>) {
        val userData = userRepository.selectByUsername(body["username"]!!)
        if (userData["role"] != "ADMIN") {
            throw UnauthorizedException()
        }

        val bookInventoryList = bookRepository.selectInventory(body["bookId"]!!.toInt(), "AVAILABLE")
        if (bookInventoryList.isEmpty()) {
            throw Exception()
        }

        val bookInventory = bookInventoryList.first()

        bookRepository.insertRentData(
            bookInventory["book_inventory_id"]!!.toInt(),
            body["username"]!!,
            LocalDateTime.now().plusDays(7)
        )
    }

    @PostMapping("/api/book/return")
    fun returnBook(@RequestBody body: Map<String, String>) {
        val userData = userRepository.selectByUsername(body["username"]!!)
        if (userData["role"] != "ADMIN") {
            throw UnauthorizedException()
        }

        val userRentedBookList = bookRepository.selectInventoryRented(body["username"]!!)

        var bookToReturn: Map<String, String>? = null
        for (rentedBook in userRentedBookList) {
            if (rentedBook["book_inventory_id"] == body["bookInventoryId"]) {
                bookToReturn = rentedBook
            }
        }

        if (bookToReturn == null) {
            throw Exception("このユーザーはこの本を借りてません。")
        }

        bookRepository.insertReturnData(
            bookToReturn["book_inventory_id"]!!.toInt(),
            body["username"]!!
        )
    }


}
