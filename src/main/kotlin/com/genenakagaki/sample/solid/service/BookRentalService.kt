package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.core.BookContent
import com.genenakagaki.sample.solid.core.BookPrice
import com.genenakagaki.sample.solid.core.UserBookRentalCampaignModel
import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.core.UserBookRentalModel
import com.genenakagaki.sample.solid.repository.UserBookRentalRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class BookRentalService(
    private val bookRepository: BookRepository,
    private val userBookRentalRepository: UserBookRentalRepository,
    private val userNotificationService: UserNotificationService,
) {
    private val campaignStartDate = LocalDate.of(2022, 4, 1).atStartOfDay()
    private val campaignEndDate = LocalDate.of(2022, 5, 1).atStartOfDay()

    fun viewBook(username: String, bookId: Int): BookContent {
        val userBookRentalModel = findUserBookRentalModel(username)
        userBookRentalModel.isBookRented(bookId)
        userBookRentalModel.isBookRented(bookId)

        val bookContent = bookRepository.findContentById(bookId)
        if (bookContent == null) {
            throw RuntimeException("本が見つかりませんでした。")
        }
        return bookContent
    }

    fun rentBook(username: String, bookId: Int) {
        val bookPrice = findBookPrice(bookId)
        val userBookRentalModel = findUserBookRentalModel(username)

        if (campaignStartDate < LocalDateTime.now() && LocalDateTime.now() < campaignEndDate) {
            val campaignModel = UserBookRentalCampaignModel(userBookRentalModel)
            campaignModel.rentBook(bookId, bookPrice.rentPrice)
            userBookRentalRepository.save(campaignModel)
        } else {
            userBookRentalModel.rentBook(bookId, bookPrice.rentPrice)
            userBookRentalRepository.save(userBookRentalModel)
        }

        userNotificationService.notifyUser(username, EmailTemplateType.RENTAL_COMPLETE)
    }

    private fun findBookPrice(bookId: Int): BookPrice {
        val bookPrice = bookRepository.findPriceById(bookId)
        if (bookPrice == null) {
            throw RuntimeException("本がみつかりませんでした。")
        }

        return bookPrice
    }

    private fun findUserBookRentalModel(username: String): UserBookRentalModel {
        val userBookRentalData = userBookRentalRepository.findByUsername(username)
        if (userBookRentalData == null) {
            throw RuntimeException("ユーザーが見つかりませんでした。")
        }
        return userBookRentalData
    }
}
