package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.core.UserBookRentalModel
import com.genenakagaki.sample.solid.repository.UserBookRentalRepository
import org.springframework.stereotype.Service

@Service
class BookRentalService(
    private val bookRepository: BookRepository,
    private val userBookRentalRepository: UserBookRentalRepository,
    private val userNotificationService: UserNotificationService,
) {

    fun viewBook(username: Any?, bookId: Any?): Any {
        val userBookRentalData = findUserBookRentalModel(username)
        UserBookRentalModel().isBookRented(bookId, userBookRentalData)

        return bookRepository.findContentById(bookId)
    }

    fun rentBook(username: Any?, bookId: Any?) {
        val bookPrice = findBookPrice(bookId)
        val userBookRentalData = findUserBookRentalModel(username)

        UserBookRentalModel().rentBook(bookId, bookPrice["rental_price"], userBookRentalData)

        userBookRentalRepository.save(userBookRentalData);
        userNotificationService.notifyUser(username, EmailTemplateType.RENTAL_COMPLETE)
    }

    private fun findBookPrice(bookId: Any?): MutableMap<String, Any?> {
        val bookPrice = bookRepository.findPriceById(bookId)
        if (bookPrice == null) {
            throw RuntimeException("本がみつかりませんでした。")
        }

        return bookPrice
    }

    private fun findUserBookRentalModel(username: Any?): MutableMap<String, Any?> {
        val userBookRentalData = userBookRentalRepository.findByUsername(username)
        if (userBookRentalData == null) {
            throw RuntimeException("ユーザーが見つかりませんでした。")
        }
        return userBookRentalData
    }
}