package com.genenakagaki.sample.solid.service

import com.genenakagaki.sample.solid.CustomException
import com.genenakagaki.sample.solid.domain.book.UserBookRentalModel
import com.genenakagaki.sample.solid.repository.BookPriceRepository
import com.genenakagaki.sample.solid.repository.UserBookRentalRepository
import org.springframework.stereotype.Service

interface BookRentalService {

    fun rentBook(username: String, bookId: Int)
    fun applyRentalPeriod(username: String)
}

@Service
class BookRentalServiceImpl(
    private val userBookRentalRepository: UserBookRentalRepository,
    private val bookPriceRepository: BookPriceRepository,
) : BookRentalService {

    override fun rentBook(username: String, bookId: Int) {
        val model: UserBookRentalModel = userBookRentalRepository.findByUsername(username)
            ?: throw CustomException("ユーザーが見つかりませんでした。")

        val bookPrice = (bookPriceRepository.findByBookId(bookId)
            ?: throw CustomException("本が見つかりませんでした。"))

        model.rentBook(bookId, bookPrice);
        userBookRentalRepository.save(model);
    }

    override fun applyRentalPeriod(username: String) {
        val model: UserBookRentalModel = userBookRentalRepository.findByUsername(username)
            ?: throw CustomException("ユーザーが見つかりませんでした。")

        model.applyRentalPeriod()
        userBookRentalRepository.save(model)
    }
}
