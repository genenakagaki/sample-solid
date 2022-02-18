package com.genenakagaki.sample.solid

import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.repository.UserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class BookManagementController(
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
) {

    @PostMapping("/api/book/add")
    fun addBook(@RequestBody body: Map<String, String>) {
        val userData = userRepository.selectByUsername(body.get("username")!!)
        if (userData.get("role") == "ADMIN") {
            bookRepository.insert(body.get("title")!!)
        }
    }

//    @PostMapping("/api/")


}
